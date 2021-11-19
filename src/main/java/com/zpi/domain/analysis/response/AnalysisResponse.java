package com.zpi.domain.analysis.response;

public record AnalysisResponse(LoginFailedResponse loginFailed,
                               TwoFactorResponse twoFactor) {
}
