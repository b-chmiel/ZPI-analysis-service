package com.zpi.infrastructure.analysis;

import com.zpi.domain.analysis.twoFactor.IncidentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
enum IncidentTypeTuple {
    DEVICE_CHANGE("DEVICE_CHANGE"),
    LOCATION_CHANGE("LOCATION_CHANGE"),
    PASSWORD_INCORRECT("PASSWORD_INCORRECT"),
    LOCKOUT("LOCKOUT"),
    AFTER_INCIDENT("AFTER_INCIDENT");


    private String name;

    static IncidentTypeTuple from(IncidentType type) {
        return IncidentTypeTuple.valueOf(type.name());
    }
}
