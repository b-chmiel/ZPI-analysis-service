package com.zpi.domain.failedLogin;

import com.zpi.domain.common.AnalysisRequest;

public interface FailedLoginService {
    void report(AnalysisRequest request);
}
