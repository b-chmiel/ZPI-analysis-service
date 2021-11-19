package com.zpi.infrastructure.analysis;

import com.zpi.domain.common.User;
import com.zpi.domain.common.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
public class JpaUserRepositoryImpl implements UserRepository {
    private final JpaUserRepo userRepo;

    @Override
    public int incrementFailedAttempts(User user) {
        var userTuple = userRepo.findByUsername(user.username());
        if (userTuple.isEmpty()) {
            return 0;
        }

        var current = userTuple.get().getFailedAttempts();
        userTuple.get().setFailedAttempts(current + 1);
        userRepo.save(userTuple.get());
        return userTuple.get().getFailedAttempts();
    }

    @Override
    public void resetFailedAttempts(User user) {
        var userTuple = userRepo.findByUsername(user.username());
        if (userTuple.isEmpty()) {
            return;
        }

        userTuple.get().setFailedAttempts(0);
        userTuple.get().setLockoutTill(null);
        userRepo.save(userTuple.get());
    }

    @Override
    public void applyLockout(User user, LocalDateTime till) {
        var userTuple = userRepo.findByUsername(user.username());
        if (userTuple.isPresent()) {
            userTuple.get().setLockoutTill(Timestamp.valueOf((till)));
            userRepo.save(userTuple.get());
        }
    }

    @Override
    public Optional<Date> getLockout(User user) {
        var tuple = userRepo.findByUsername(user.username());
        return tuple.map(UserTuple::getLockoutTill);
    }

    public interface JpaUserRepo extends JpaRepository<UserTuple, Long> {
        Optional<UserTuple> findByUsername(String username);
    }
}
