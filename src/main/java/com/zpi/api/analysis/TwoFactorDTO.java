package com.zpi.api.analysis;

import com.zpi.domain.analysis.TwoFactorResponse;
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

    public TwoFactorResponse toDomain() {
        return new TwoFactorResponse(additionalLayerRequired);
    }
}
