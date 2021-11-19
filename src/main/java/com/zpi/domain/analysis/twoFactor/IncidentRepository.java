package com.zpi.domain.analysis.twoFactor;

import com.zpi.domain.common.AnalysisRequest;
import com.zpi.domain.common.User;

public interface IncidentRepository {
    void save(Incident incident, AnalysisRequest request);

    boolean isLastRequestIncident(User user);
}
