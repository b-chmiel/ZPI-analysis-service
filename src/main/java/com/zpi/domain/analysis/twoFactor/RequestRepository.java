package com.zpi.domain.analysis.twoFactor;

import com.zpi.domain.analysis.request.AnalysisRequest;
import com.zpi.domain.analysis.request.User;

import java.util.Optional;

public interface RequestRepository {
    void save(AnalysisRequest request);

    Optional<AnalysisRequest> retrieveLastEntry(User user);
}
