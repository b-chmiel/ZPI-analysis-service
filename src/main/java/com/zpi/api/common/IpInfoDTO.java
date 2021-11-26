package com.zpi.api.common;

import com.zpi.domain.common.IpInfo;

public record IpInfoDTO(String city, String continentCode, String continentName,
                        String countryCode, String countryName, String ipAddress,
                        String stateProv) {
    public IpInfoDTO(IpInfo info) {
        this(
                info.getCity(),
                info.getContinentCode(),
                info.getContinentName(),
                info.getCountryCode(),
                info.getCountryName(),
                info.getIpAddress(),
                info.getStateProv()
        );
    }

    public IpInfo toDomain() {
        return new IpInfo(
                city,
                continentCode,
                continentName,
                countryCode,
                countryName,
                ipAddress,
                stateProv
        );
    }
}
