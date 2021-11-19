package com.zpi.domain.analysis.twoFactor;

import lombok.Getter;

import java.util.List;

@Getter
public class Incident {
    private final List<IncidentType> type;
    private final IncidentSeverity severity;

    public Incident(List<IncidentType> type) {
        this.type = type;
        this.severity = evaluateSeverity(type);
    }

    private static IncidentSeverity evaluateSeverity(List<IncidentType> types) {
        var severityValue = types.stream()
                .mapToInt(IncidentType::getSeverity)
                .reduce(0, Integer::sum);

        switch (severityValue) {
            case 0, 1 -> {
                return IncidentSeverity.LOW;
            }
            case 2 -> {
                return IncidentSeverity.MEDIUM;
            }
            default -> {
                return IncidentSeverity.HIGH;
            }
        }
    }

}
