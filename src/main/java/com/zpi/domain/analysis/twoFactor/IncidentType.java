package com.zpi.domain.analysis.twoFactor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IncidentType {
    DEVICE_CHANGE("DEVICE_CHANGE"),
    LOCATION_CHANGE("LOCATION_CHANGE"),
    ALL_METADATA_CHANGE("ALL_METADATA_CHANGE"),
    PASSWORD_INCORRECT("PASSWORD_INCORRECT");

    private final String name;
}
