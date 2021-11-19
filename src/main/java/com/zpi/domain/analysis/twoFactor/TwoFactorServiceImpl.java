package com.zpi.domain.analysis.twoFactor;

import com.zpi.domain.analysis.response.TwoFactorResponse;
import com.zpi.domain.common.AnalysisRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
        var incidentTypes = new ArrayList<IncidentType>();
        if (incidentRepository.isLastRequestIncident(request.user())) {
            incidentTypes.add(IncidentType.AFTER_INCIDENT);
        }

        var lastEntry = requestRepository.retrieveLastEntry(request.user());

        if (lastEntry.isEmpty()) {
            return Optional.empty();
        }
        incidentTypes.addAll(detectMetadataChangeIncident(request, lastEntry.get()));
        return incidentTypes.size() == 0 ? Optional.empty() : Optional.of(new Incident(incidentTypes));
    }

    private List<IncidentType> detectMetadataChangeIncident(AnalysisRequest current, AnalysisRequest previous) {
        var result = new ArrayList<IncidentType>();

        if (!previous.deviceInfo().equals(current.deviceInfo())) {
            result.add(IncidentType.DEVICE_CHANGE);
        }

        if (!previous.ipInfo().equals(current.ipInfo())) {
            result.add(IncidentType.LOCATION_CHANGE);
        }

        return result;
    }

    private void saveIncident(Incident incident, AnalysisRequest request) {
        incidentRepository.save(incident, request);
    }

    private void saveAnalysisData(AnalysisRequest request) {
        requestRepository.save(request);
    }
}
