package com.zpi.domain.analysis.lockout;

import com.zpi.domain.common.Lockout;
import com.zpi.domain.common.User;

public interface LockoutService {
    Lockout evaluate(User user);
}
