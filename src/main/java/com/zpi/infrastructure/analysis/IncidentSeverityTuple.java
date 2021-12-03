package com.zpi.infrastructure.analysis;

import com.zpi.domain.analysis.twoFactor.incident.IncidentSeverity;
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

    static IncidentSeverity toDomain(IncidentSeverityTuple severity) {
        return IncidentSeverity.valueOf(severity.getName());
    }
}
