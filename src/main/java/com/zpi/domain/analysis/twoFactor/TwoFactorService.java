package com.zpi.domain.analysis.twoFactor;

import com.zpi.domain.analysis.request.AnalysisRequest;
import com.zpi.domain.analysis.response.TwoFactorResponse;

public interface TwoFactorService {
    TwoFactorResponse evaluate(AnalysisRequest request);
}
