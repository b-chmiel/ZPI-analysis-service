package com.zpi.domain.analysis.lockout;

import com.zpi.domain.analysis.response.LoginFailedResponse;
import com.zpi.domain.common.AnalysisRequest;

public interface LockoutService {
    LoginFailedResponse evaluate(AnalysisRequest request);
}
