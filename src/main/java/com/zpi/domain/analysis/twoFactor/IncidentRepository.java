package com.zpi.domain.analysis.twoFactor;

import com.zpi.domain.common.AnalysisRequest;

public interface IncidentRepository {
    void save(Incident incident, AnalysisRequest request);
}
