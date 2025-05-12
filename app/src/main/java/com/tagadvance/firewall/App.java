package com.tagadvance.firewall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.net.util.SubnetUtils.SubnetInfo;

/**
 * Please ignore the quality of the code. This was quick and dirty.
 */
public class App {

	public static void main(String[] args) throws IOException {
		//printStats();
		generateAddressLists();
	}

	public static void generateAddressLists() throws IOException {
		final var countryNameByCode = ipToCountry().stream()
			.collect(
				Collectors.toMap(IpRecord::getCountryCode, IpRecord::getCountryName, (a, b) -> a,
					HashMap::new));

		final var ipRecords = ipToCountry().stream()
			.collect(Collectors.groupingBy(IpRecord::getCountryCode));

		final var addressLists = Path.of("../address_lists");
		countryNameByCode.keySet().stream().sorted().forEach(countryCode -> {
			final var country = countryNameByCode.get(countryCode)
				.replaceAll("\\s", "_")
				.replaceAll("\\W*", "");

			final var countryFile = addressLists.resolve(Path.of("%s.rsc".formatted(country)));
			try {
				Files.writeString(countryFile, "/ip firewall address-list\n",
					StandardCharsets.UTF_8, StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);
				for (final var record : ipRecords.get(countryCode)) {
					Files.writeString(countryFile,
						"add address=%s list=%s%n".formatted(record.getCidr(), country),
						StandardCharsets.UTF_8, StandardOpenOption.APPEND);
				}
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public static void printStats() throws IOException {
		final var ipRecords = ipToCountry();

		final var countryNameByIpAddress = fail2ban().stream()
			.collect(Collectors.toMap(Function.identity(), ipAddress -> {
				final var ipAddressInt = toInteger(ipAddress);

				return ipRecords.stream()
					.parallel()
					.filter(r -> r.getLow() <= ipAddressInt && r.getHigh() >= ipAddressInt)
					.findAny()
					.map(IpRecord::getCountryName)
					.orElse("UNKNOWN");
			}));

		countryNameByIpAddress.values()
			.stream()
			.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
			.forEach((countryName, count) -> System.out.printf("%s: %d%n", countryName, count));
	}

	public static List<String> fail2ban() throws IOException {
		return readLines("/fail2ban.txt");
	}

	public static List<IpRecord> ipToCountry() throws IOException {
		return readLines("/ip-to-country.csv").stream()
			// ignore header
			.skip(1).map(line -> {
				final var tokens = line.trim().split(",");
				if (tokens.length == 4) {
					try {
						return new IpRecord(tokens[0], tokens[2], tokens[3]);
					} catch (final IllegalArgumentException e) {
						// do nothing
					}
				}

				return null;
			}).filter(Objects::nonNull).toList();
	}

	private static List<String> readLines(final String resource) throws IOException {
		final var list = new ArrayList<String>();

		try (final var in = new BufferedReader(
			new InputStreamReader(App.class.getResourceAsStream(resource)))) {
			String line;
			while ((line = in.readLine()) != null) {
				list.add(line.trim());
			}
		}

		return list;
	}

	public static class IpRecord {

		private final String cidr;
		private final String countryCode;
		private final String countryName;
		private final int low, high;

		public IpRecord(final String cidr, final String countryCode, final String countryName) {
			this.cidr = cidr;
			this.countryCode = countryCode;
			this.countryName = countryName;

			final SubnetInfo subnetInfo = new SubnetUtils(cidr).getInfo();
			this.low = toInteger(subnetInfo.getLowAddress());
			this.high = toInteger(subnetInfo.getHighAddress());
		}

		public String getCidr() {
			return cidr;
		}

		public String getCountryCode() {
			return countryCode;
		}

		public String getCountryName() {
			return countryName;
		}

		public int getLow() {
			return low;
		}

		public int getHigh() {
			return high;
		}

	}

	private static int toInteger(final String ipv4) {
		final var octets = ipv4.split("\\.");

		int addr = 0;
		for (int i = 0; i < 4; ++i) {
			final int n = Integer.parseInt(octets[i]);
			addr |= (n & 0xff) << 8 * (4 - i);
		}

		return addr;
	}

}
