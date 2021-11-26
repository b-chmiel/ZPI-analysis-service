package com.zpi.api.report;

import com.zpi.domain.analysis.twoFactor.incident.Incident;

import java.util.List;
import java.util.stream.Collectors;

record IncidentDTO(
        List<String> type,
        String severity
) {
    IncidentDTO(Incident i) {
        this(i.getType().stream().map(Enum::toString).collect(Collectors.toList()), i.getSeverity().toString());
    }

}
