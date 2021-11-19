package com.zpi.domain.analysis.twoFactor.incident;

import com.zpi.domain.common.AnalysisRequest;
import com.zpi.domain.common.User;

import java.util.Optional;

public interface IncidentRepository {
    void save(Incident incident, AnalysisRequest request);

    Optional<Incident> lastIncident(User user);
}
