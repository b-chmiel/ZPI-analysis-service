package com.zpi.domain.failedLogin;

import com.zpi.domain.analysis.twoFactor.incident.Incident;
import com.zpi.domain.analysis.twoFactor.incident.IncidentRepository;
import com.zpi.domain.analysis.twoFactor.incident.IncidentType;
import com.zpi.domain.common.AnalysisRequest;
import com.zpi.domain.common.User;
import com.zpi.domain.common.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FailedLoginServiceImpl implements FailedLoginService {
    private static final int SECONDS_LOCKOUT_DELAY = 20;
    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;

    @Override
    public void report(AnalysisRequest request) {
        var currentFailedAttempts = userRepository.incrementFailedAttempts(request.user());

        var incident = evaluate(request, currentFailedAttempts);

        incidentRepository.save(incident, request);
    }

    private Incident evaluate(AnalysisRequest request, int currentFailedAttempts) {
        if (currentFailedAttempts >= 3) {
            handleLockout(request.user());
            return new Incident(IncidentType.LOCKOUT);
        } else {
            return new Incident(IncidentType.PASSWORD_INCORRECT);
        }
    }

    private void handleLockout(User user) {
        var till = LocalDateTime.now().plusSeconds(SECONDS_LOCKOUT_DELAY);
        userRepository.applyLockout(user, till);
    }
}
