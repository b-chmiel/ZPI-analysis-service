package com.zpi.domain.analysis;

import com.zpi.domain.analysis.lockout.LockoutService;
import com.zpi.domain.analysis.twoFactor.TwoFactorService;
import com.zpi.domain.common.AnalysisRequest;
import com.zpi.domain.common.Lockout;
import com.zpi.domain.common.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalysisServiceImpl implements AnalysisService {
    private final TwoFactorService twoFactorService;
    private final LockoutService lockoutService;

    @Override
    public AnalysisResponse analyse(AnalysisRequest request) {
        var loginFailed = lockoutService.evaluate(request.user());
        var twoFactor = twoFactorService.evaluate(request);
        return new AnalysisResponse(loginFailed, twoFactor);
    }

    @Override
    public Lockout lockoutInfo(User user) {
        return lockoutService.evaluate(user);
    }
}
