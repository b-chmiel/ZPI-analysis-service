package com.zpi.domain.analysis.twoFactor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IncidentType {
    DEVICE_CHANGE("DEVICE_CHANGE", 0),
    LOCATION_CHANGE("LOCATION_CHANGE", 0),
    PASSWORD_INCORRECT("PASSWORD_INCORRECT", 0),
    LOCKOUT("LOCKOUT", 2),
    AFTER_INCIDENT("AFTER_INCIDENT", 1);

    private final String name;
    private final int severity;
}
