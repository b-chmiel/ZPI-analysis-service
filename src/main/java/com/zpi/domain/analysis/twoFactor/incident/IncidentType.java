package com.zpi.domain.analysis.twoFactor.incident;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IncidentType {
    DEVICE_CHANGE("DEVICE_CHANGE", 1),
    LOCATION_CHANGE("LOCATION_CHANGE", 1),
    PASSWORD_INCORRECT("PASSWORD_INCORRECT", 1),
    LOCKOUT("LOCKOUT", 2),
    AFTER_INCIDENT("AFTER_INCIDENT", 1),
    REQUEST_DESPITE_LOCKOUT("REQUEST_DESPITE_LOCKOUT", 1),
    FIRST_LOGIN("FIRST_LOGIN", 1),
    SUSPICIOUS_USER("SUSPICIOUS_USER", 1),
    SUSPICIOUS_COUNTRY("SUSPICIOUS_COUNTRY", 2);

    private final String name;
    private final int severity;
}
