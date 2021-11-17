package com.zpi.domain.analysis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnalysisResponse {
    private final LoginFailedResponse loginFailed;
    private final TwoFactorResponse twoFactor;
}
