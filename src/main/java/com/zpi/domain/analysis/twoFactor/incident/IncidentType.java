package com.zpi.domain.analysis.twoFactor.incident;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IncidentType {
    DEVICE_CHANGE("DEVICE_CHANGE", 0),
    LOCATION_CHANGE("LOCATION_CHANGE", 0),
    PASSWORD_INCORRECT("PASSWORD_INCORRECT", 0),
    LOCKOUT("LOCKOUT", 2),
    AFTER_INCIDENT("AFTER_INCIDENT", 1),
    REQUEST_DESPITE_LOCKOUT("REQUEST_DESPITE_LOCKOUT", 1),
    FIRST_LOGIN("FIRST_LOGIN", 0),
    SUSPICIOUS_USER("SUSPICIOUS_USER", 1),
    SUSPICIOUS_COUNTRY("SUSPICIOUS_COUNTRY", 1);

    private final String name;
    private final int severity;
}
