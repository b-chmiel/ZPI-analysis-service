package com.zpi.domain.analysis.lockoutService;

import com.zpi.domain.analysis.response.LoginAction;
import com.zpi.domain.analysis.response.LoginFailedResponse;
import com.zpi.domain.common.AnalysisRequest;
import com.zpi.domain.common.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class LockoutServiceImpl implements LockoutService {
    private final UserRepository userRepository;

    @Override
    public LoginFailedResponse evaluate(AnalysisRequest request) {
        var lockout = userRepository.getLockout(request.user());

        var now = new Date();
        if (lockout.isPresent()) {
            var date = lockout.get();
            if (date.before(now)) {
                userRepository.resetFailedAttempts(request.user());
                return new LoginFailedResponse(LoginAction.ALLOW, now);
            }

            return new LoginFailedResponse(LoginAction.BLOCK, date);
        }

        return new LoginFailedResponse(LoginAction.ALLOW, now);
    }
}
