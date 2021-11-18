package com.zpi.domain.analysis.twoFactor;

import com.zpi.domain.analysis.request.AnalysisRequest;

public interface IncidentRepository {
    void save(Incident incident, AnalysisRequest request);
}
