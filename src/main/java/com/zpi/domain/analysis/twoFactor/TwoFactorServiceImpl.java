package com.zpi.domain.analysis.twoFactor;

import com.zpi.domain.analysis.request.AnalysisRequest;
import com.zpi.domain.analysis.response.TwoFactorResponse;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TwoFactorServiceImpl implements TwoFactorService {
    @Override
    public TwoFactorResponse evaluate(AnalysisRequest request) {
        var incident = detectIncident(request);

        if (incident.isPresent()) {
            saveIncident(incident, request);
            return new TwoFactorResponse(true);
        }

        saveAnalysisData(request);
        return new TwoFactorResponse(false);
    }

    private Optional<Incident> detectIncident(AnalysisRequest request) {
        return Optional.empty();
    }

    private void saveIncident(Optional<Incident> incident, AnalysisRequest request) {
    }

    private void saveAnalysisData(AnalysisRequest request) {
    }
}
