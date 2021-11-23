package com.zpi.api.analysis;

import com.zpi.domain.analysis.TwoFactorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
class TwoFactorDTO {
    private boolean additionalLayerRequired;

    TwoFactorDTO(TwoFactorResponse response) {
        additionalLayerRequired = response.isAdditionalLayerRequired();
    }
}
