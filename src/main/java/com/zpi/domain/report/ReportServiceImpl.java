package com.zpi.domain.report;

import com.zpi.domain.analysis.twoFactor.incident.IncidentRepository;
import com.zpi.domain.common.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final IncidentRepository repository;

    @Override
    public List<RequestIncident> incidents(User user) {
        return repository.incidents(user);
    }
}
