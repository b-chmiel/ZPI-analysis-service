package com.zpi.domain.analysis;

import com.zpi.domain.common.AnalysisRequest;
import com.zpi.domain.common.Lockout;
import com.zpi.domain.common.User;

public interface AnalysisService {
    AnalysisResponse analyse(AnalysisRequest request);
    Lockout lockoutInfo(User user);
}
