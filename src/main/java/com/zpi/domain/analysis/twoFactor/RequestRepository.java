package com.zpi.domain.analysis.twoFactor;

import com.zpi.domain.common.AnalysisRequest;
import com.zpi.domain.common.User;

import java.util.Optional;

public interface RequestRepository {
    void save(AnalysisRequest request);

    Optional<AnalysisRequest> retrieveLastEntry(User user);
}
