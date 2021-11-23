package com.zpi.domain.analysis.lockout;

import com.zpi.domain.common.Lockout;
import com.zpi.domain.common.LockoutMode;
import com.zpi.domain.common.User;
import com.zpi.domain.common.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class LockoutServiceImpl implements LockoutService {
    private final UserRepository userRepository;

    @Override
    public Lockout evaluate(User user) {
        var lockout = userRepository.getLockout(user);

        var now = new Date();
        if (lockout.isPresent()) {
            var date = lockout.get();
            if (date.before(now)) {
                userRepository.resetFailedAttempts(user);
                return new Lockout(LockoutMode.ALLOW, now);
            }

            return new Lockout(LockoutMode.BLOCK, date);
        }

        return new Lockout(LockoutMode.ALLOW, now);
    }
}
