package com.zpi.domain.analysis;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnalysisServiceImpl implements AnalysisService {
    @Override
    public AnalysisResponse analyse(AnalysisRequest request) {
        var loginFailed = evaluateLoginFailed(request);
        var twoFactor = evaluateTwoFactor(request);
        return new AnalysisResponse(loginFailed, twoFactor);
    }

    private LoginFailedResponse evaluateLoginFailed(AnalysisRequest request) {
        return new LoginFailedResponse(LoginAction.BLOCK, LocalDateTime.now());
    }

    private TwoFactorResponse evaluateTwoFactor(AnalysisRequest request) {
        return new TwoFactorResponse(false);
    }
}
