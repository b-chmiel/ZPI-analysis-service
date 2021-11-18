package com.zpi.domain.failedLogin;

import com.zpi.domain.analysis.twoFactor.Incident;
import com.zpi.domain.analysis.twoFactor.IncidentRepository;
import com.zpi.domain.analysis.twoFactor.IncidentSeverity;
import com.zpi.domain.analysis.twoFactor.IncidentType;
import com.zpi.domain.common.AnalysisRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FailedLoginServiceImpl implements FailedLoginService {
    private final IncidentRepository repository;

    @Override
    public void report(AnalysisRequest request) {
        var incident = new Incident(IncidentType.PASSWORD_INCORRECT, IncidentSeverity.MEDIUM);
        repository.save(incident, request);
    }
}
