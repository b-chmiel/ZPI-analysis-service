package com.zpi.domain.analysis;

import com.zpi.domain.common.Lockout;

public record AnalysisResponse(Lockout lockout,
                               TwoFactorResponse twoFactor) {
}
