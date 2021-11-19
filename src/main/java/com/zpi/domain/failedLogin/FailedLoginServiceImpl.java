package com.zpi.domain.failedLogin;

import com.zpi.domain.analysis.twoFactor.incident.Incident;
import com.zpi.domain.analysis.twoFactor.incident.IncidentRepository;
import com.zpi.domain.analysis.twoFactor.incident.IncidentType;
import com.zpi.domain.common.AnalysisRequest;
import com.zpi.domain.common.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FailedLoginServiceImpl implements FailedLoginService {
    private static final int SECONDS_LOCKOUT_DELAY = 30;
    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;

    @Override
    public void report(AnalysisRequest request) {
        var currentFailedAttempts = userRepository.incrementFailedAttempts(request.user());

        Incident incident = evaluate(request, currentFailedAttempts);

        incidentRepository.save(incident, request);
    }

    private Incident evaluate(AnalysisRequest request, int currentFailedAttempts) {
        Incident incident;
        if (currentFailedAttempts >= 3) {
            handleLockout(request);
            incident = new Incident(List.of(IncidentType.LOCKOUT));
        } else {
            incident = new Incident(List.of(IncidentType.PASSWORD_INCORRECT));
        }
        return incident;
    }

    private void handleLockout(AnalysisRequest request) {
        var till = LocalDateTime.now().plusSeconds(SECONDS_LOCKOUT_DELAY);
        userRepository.applyLockout(request.user(), till);
    }
}
