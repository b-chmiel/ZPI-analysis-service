package com.zpi.domain.analysis.twoFactor;

import com.zpi.domain.analysis.TwoFactorResponse;
import com.zpi.domain.common.AnalysisRequest;

public interface TwoFactorService {
    TwoFactorResponse evaluate(AnalysisRequest request);
}
