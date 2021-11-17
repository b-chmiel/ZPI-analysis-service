package com.zpi.domain.analysis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TwoFactorResponse {
    private final boolean isAdditionalLayerRequired;
}
