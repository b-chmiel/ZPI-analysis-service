package com.zpi.domain.analysis.twoFactor;

import com.zpi.domain.analysis.response.TwoFactorResponse;
import com.zpi.domain.common.AnalysisRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TwoFactorServiceImpl implements TwoFactorService {
    private final RequestRepository requestRepository;
    private final IncidentRepository incidentRepository;

    @Override
    public TwoFactorResponse evaluate(AnalysisRequest request) {
        var incident = detectIncident(request);

        if (incident.isPresent()) {
            saveIncident(incident.get(), request);
            return new TwoFactorResponse(true);
        }

        saveAnalysisData(request);
        return new TwoFactorResponse(false);
    }

    private Optional<Incident> detectIncident(AnalysisRequest request) {
        var lastEntry = requestRepository.retrieveLastEntry(request.user());

        if (lastEntry.isEmpty()) {
            return Optional.empty();
        }

        IncidentType type = null;
        IncidentSeverity severity = null;

        if (!lastEntry.get().deviceInfo().equals(request.deviceInfo())) {
            type = IncidentType.DEVICE_CHANGE;
            severity = IncidentSeverity.MEDIUM;
        }

        if (!lastEntry.get().ipInfo().equals(request.ipInfo())) {
            if (type != null) {
                type = IncidentType.ALL_METADATA_CHANGE;
                severity = IncidentSeverity.HIGH;
            } else {
                type = IncidentType.LOCATION_CHANGE;
                severity = IncidentSeverity.LOW;
            }
        }

        return type == null ? Optional.empty() : Optional.of(new Incident(type, severity));
    }

    private void saveIncident(Incident incident, AnalysisRequest request) {
        incidentRepository.save(incident, request);
    }

    private void saveAnalysisData(AnalysisRequest request) {
        requestRepository.save(request);
    }
}
