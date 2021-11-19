package com.zpi.api.analysis.dto;

import com.zpi.domain.analysis.response.TwoFactorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TwoFactorDTO {
    private boolean additionalLayerRequired;

    public TwoFactorDTO(TwoFactorResponse response) {
        additionalLayerRequired = response.isAdditionalLayerRequired();
    }
}
