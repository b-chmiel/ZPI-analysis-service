package com.zpi.api.analysis.dto;

import com.zpi.domain.analysis.response.AnalysisResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnalysisResponseDTO {
    private final LoginFailedDTO loginFailed;
    private final TwoFactorDTO twoFactor;

    public AnalysisResponseDTO(AnalysisResponse result) {
        loginFailed = new LoginFailedDTO(result.loginFailed());
        twoFactor = new TwoFactorDTO(result.twoFactor());
    }
}
