package com.zpi.api.analysis;

import com.zpi.domain.analysis.AnalysisResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class AnalysisResponseDTO {
    private final LockoutDTO loginFailed;
    private final TwoFactorDTO twoFactor;

    AnalysisResponseDTO(AnalysisResponse result) {
        loginFailed = new LockoutDTO(result.lockout());
        twoFactor = new TwoFactorDTO(result.twoFactor());
    }
}
