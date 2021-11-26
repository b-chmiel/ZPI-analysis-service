package com.zpi.domain.analysis.twoFactor.incident;

import com.zpi.domain.common.AnalysisRequest;
import com.zpi.domain.common.User;
import com.zpi.domain.report.RequestIncident;

import java.util.List;
import java.util.Optional;

public interface IncidentRepository {
    void save(Incident incident, AnalysisRequest request);

    Optional<Incident> lastIncident(User user);

    List<RequestIncident> incidents(User user);
}
