package com.zpi.api.analysis;

import com.zpi.domain.analysis.AnalysisResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnalysisResponseDTO {
    private final LoginFailedDTO loginFailed;
    private final TwoFactorDTO twoFactor;

    public AnalysisResponseDTO(AnalysisResponse result) {
        loginFailed = new LoginFailedDTO(result.getLoginFailed());
        twoFactor = new TwoFactorDTO(result.getTwoFactor());
    }
}
