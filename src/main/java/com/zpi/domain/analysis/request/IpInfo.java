package com.zpi.domain.analysis.request;


public record IpInfo(String city, String continentCode, String continentName,
                     String countryCode, String countryName, String ipAddress,
                     String stateProv) {
}
