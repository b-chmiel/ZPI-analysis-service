package com.zpi.domain.analysis.twoFactor.incident;

import com.zpi.domain.common.AnalysisRequest;

import java.util.Optional;

public interface IncidentDetectionService {
    Optional<Incident> detect(AnalysisRequest request);
}
