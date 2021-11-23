package com.zpi.domain.analysis.twoFactor;

import com.zpi.domain.analysis.TwoFactorResponse;
import com.zpi.domain.analysis.twoFactor.incident.Incident;
import com.zpi.domain.analysis.twoFactor.incident.IncidentDetectionService;
import com.zpi.domain.analysis.twoFactor.incident.IncidentRepository;
import com.zpi.domain.common.AnalysisRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TwoFactorServiceImpl implements TwoFactorService {
    private final RequestRepository requestRepository;
    private final IncidentRepository incidentRepository;
    private final IncidentDetectionService incidentDetection;

    @Override
    public TwoFactorResponse evaluate(AnalysisRequest request) {
        var incident = incidentDetection.detect(request);

        if (incident.isPresent()) {
            saveIncident(incident.get(), request);
            return new TwoFactorResponse(true);
        }

        saveAnalysisData(request);
        return new TwoFactorResponse(false);
    }

    private void saveIncident(Incident incident, AnalysisRequest request) {
        incidentRepository.save(incident, request);
    }

    private void saveAnalysisData(AnalysisRequest request) {
        requestRepository.save(request);
    }
}
