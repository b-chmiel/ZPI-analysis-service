package com.zpi.api.analysis.request;

import com.zpi.domain.analysis.request.IpInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IpInfoDTO {
    private final String city;
    private final String continentCode;
    private final String continentName;
    private final String countryCode;
    private final String countryName;
    private final String ipAddress;
    private final String stateProv;

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
