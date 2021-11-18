package com.zpi.domain.analysis;

import com.zpi.domain.common.AnalysisRequest;
import com.zpi.domain.analysis.response.AnalysisResponse;
import com.zpi.domain.analysis.response.LoginAction;
import com.zpi.domain.analysis.response.LoginFailedResponse;
import com.zpi.domain.analysis.twoFactor.TwoFactorService;
import com.zpi.domain.common.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnalysisServiceImpl implements AnalysisService {
    private final TwoFactorService twoFactorService;

    @Override
    public AnalysisResponse analyse(AnalysisRequest request) {
        var loginFailed = evaluateLoginFailed(request);
        var twoFactor = twoFactorService.evaluate(request);
        return new AnalysisResponse(loginFailed, twoFactor);
    }

    private LoginFailedResponse evaluateLoginFailed(AnalysisRequest request) {
        return new LoginFailedResponse(LoginAction.ALLOW, LocalDateTime.now());
    }
}
