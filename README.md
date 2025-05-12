# Tarpit

Firewall stats and country address lists.

## Synopsis

To improve my network security, I set up a tarpit on my router. Any incoming traffic on ports
20–23 is added to a permanent blocklist. If you do this yourself, you'll have to move all telnet,
FTP, and/or SSH servers to non-standard ports, or, better yet, use a VPN. This has proved to be
an effective way to limit unsolicited traffic.

## Address Lists

There are already more than 40,000 individual IP addresses in my blocklist. This isn't sustainable,
so I've decided to generate [RouterOS-compatible address lists](address_lists) for every country and
block the worst offenders (excluding the United States). Fixed size address lists will hopefully
improve performance.

## Count of Banned IP Addresses Per Country

I won't publish the naughty list in case it violates some privacy policy I'm not aware of, but you
can find the aggregated report below:

```text
China: 7030
United States: 6036
Taiwan: 5421
India: 3321
South Korea: 2485
Russia: 1272
Brazil: 1256
Germany: 934
Japan: 899
Hong Kong: 840
United Kingdom: 791
The Netherlands: 737
Singapore: 700
Thailand: 568
Ukraine: 564
Vietnam: 532
Italy: 529
Australia: 481
Turkey: 414
Canada: 405
Sweden: 390
France: 370
Iran: 340
Bulgaria: 323
Pakistan: 296
Malaysia: 281
Spain: 232
Mexico: 187
Argentina: 186
Poland: 180
Romania: 169
Indonesia: 168
Kuwait: 114
Venezuela: 103
Egypt: 103
Nigeria: 95
Ethiopia: 94
Switzerland: 90
Portugal: 86
Israel: 82
Finland: 77
Seychelles: 76
Hungary: 74
Lithuania: 72
Uruguay: 71
South Africa: 69
Greece: 63
United Arab Emirates: 58
Chile: 56
Macao: 53
Colombia: 50
Czechia: 50
Tanzania: 49
Norway: 48
Kazakhstan: 43
Mozambique: 41
Philippines: 41
Bangladesh: 40
Morocco: 38
Moldova: 35
???: 33
Denmark: 32
Belgium: 31
New Zealand: 30
Latvia: 29
Armenia: 27
Bolivia: 27
Algeria: 26
North Macedonia: 25
Cambodia: 24
Austria: 24
Belarus: 24
Slovakia: 23
Costa Rica: 21
Tunisia: 21
Dominican Republic: 21
Kenya: 20
Cabo Verde: 20
Honduras: 20
Uzbekistan: 17
Azerbaijan: 16
Albania: 16
Saudi Arabia: 16
Panama: 15
Georgia: 14
Ireland: 14
Syria: 12
Nepal: 12
Cyprus: 12
Serbia: 12
Iraq: 11
Bahrain: 11
Malta: 11
Paraguay: 10
Jordan: 10
Lebanon: 10
Luxembourg: 10
Mauritius: 10
Zambia: 9
Mongolia: 8
Palestinian Territory: 8
Peru: 8
Slovenia: 8
Trinidad and Tobago: 7
Croatia: 7
Estonia: 7
Senegal: 7
Puerto Rico: 6
Sri Lanka: 6
Ecuador: 6
Guatemala: 5
Iceland: 5
Afghanistan: 5
Kyrgyzstan: 5
Bhutan: 5
Suriname: 5
Angola: 4
New Caledonia: 4
Jamaica: 4
Sint Maarten: 4
Bosnia and Herzegovina: 4
Zimbabwe: 4
Ghana: 3
French Polynesia: 3
Brunei: 3
Myanmar: 3
Cameroon: 3
El Salvador: 3
Monaco: 3
Vanuatu: 3
Qatar: 3
U.S. Virgin Islands: 3
Reunion: 3
British Virgin Islands: 3
Uganda: 3
Montenegro: 3
Democratic Republic of the Congo: 2
Turkmenistan: 2
Aruba: 2
Ivory Coast: 2
Libya: 2
Andorra: 2
Liberia: 2
Cuba: 2
Guam: 2
Barbados: 2
Botswana: 2
Benin: 1
Guadeloupe: 1
Laos: 1
Namibia: 1
Bermuda: 1
Fiji: 1
Somalia: 1
Madagascar: 1
Liechtenstein: 1
Cook Islands: 1
Burkina Faso: 1
Mauritania: 1
Tajikistan: 1
Northern Mariana Islands: 1
Oman: 1
Gabon: 1
Curacao: 1
Eswatini: 1
Burundi: 1
Sierra Leone: 1
Kosovo: 1
Nicaragua: 1
Greenland: 1
```

IP address data powered by [IPLocate.io](https://github.com/iplocate/ip-address-databases)
