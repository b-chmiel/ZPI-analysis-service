package com.zpi.infrastructure.analysis;

import com.zpi.domain.analysis.twoFactor.IncidentSeverity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
enum IncidentSeverityTuple {
    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH");

    private final String name;

    static IncidentSeverityTuple from(IncidentSeverity severity) {
        return IncidentSeverityTuple.valueOf(severity.name());
    }
}
