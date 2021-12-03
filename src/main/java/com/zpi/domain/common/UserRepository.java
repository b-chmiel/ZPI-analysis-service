package com.zpi.domain.common;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public interface UserRepository {
    int incrementFailedAttempts(User user);

    void resetFailedAttempts(User user);

    void applyLockout(User user, LocalDateTime till);

    Optional<Date> getLockout(User user);

    Optional<Long> userId(User user);

    Optional<User> user(String username);
}
