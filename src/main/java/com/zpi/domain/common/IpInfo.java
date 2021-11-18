package com.zpi.domain.common;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Getter
@EqualsAndHashCode
public class IpInfo {
    private final String fingerprint;
    private final String city;
    private final String continentCode;
    private final String continentName;
    private final String countryCode;
    private final String countryName;
    private final String ipAddress;
    private final String stateProv;

    public IpInfo(
            String city,
            String continentCode,
            String continentName,
            String countryCode,
            String countryName,
            String ipAddress,
            String stateProv
    ) {
        this.fingerprint = createFingerprint(city, continentCode, continentName, countryCode, countryName, ipAddress, stateProv);
        this.city = city;
        this.continentCode = continentCode;
        this.continentName = continentName;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.ipAddress = ipAddress;
        this.stateProv = stateProv;
    }

    @SneakyThrows
    private String createFingerprint(String city, String continentCode, String continentName, String countryCode, String countryName, String ipAddress, String stateProv) {
        var text = city + continentCode + continentName + countryCode + countryName + ipAddress + stateProv;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }
}
