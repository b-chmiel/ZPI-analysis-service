package com.zpi.domain.analysis.twoFactor.incident;

import com.zpi.domain.analysis.twoFactor.RequestRepository;
import com.zpi.domain.common.AnalysisRequest;
import com.zpi.domain.common.User;
import com.zpi.domain.common.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IncidentDetectionServiceImpl implements IncidentDetectionService {
    private final RequestRepository requestRepository;
    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<Incident> detect(AnalysisRequest request) {
        var lastEntry = requestRepository.retrieveLastEntry(request.user());

        if (lastEntry.isEmpty()) {
            return Optional.of(new Incident(IncidentType.FIRST_LOGIN));
        }

        var incidentTypes = new ArrayList<IncidentType>();

        incidentTypes.addAll(detectRequestDespiteLockout(request.user()));
        incidentTypes.addAll(detectAfterIncidentEvent(request.user()));
        incidentTypes.addAll(detectMetadataChangeIncident(request, lastEntry.get()));
        incidentTypes.addAll(detectWhenUserExceedsLimit(request));
        incidentTypes.addAll(detectWhenCountryExceedsLimit(request));

        return incidentTypes.size() == 0 ? Optional.empty() : Optional.of(new Incident(incidentTypes));
    }

    private List<IncidentType> detectRequestDespiteLockout(User user) {
        var lockout = userRepository.getLockout(user);

        if (lockout.isPresent()) {
            return List.of(IncidentType.REQUEST_DESPITE_LOCKOUT);
        }

        return new ArrayList<>();
    }

    private List<IncidentType> detectAfterIncidentEvent(User user) {
        var last = incidentRepository.lastIncident(user);

        if (last.isEmpty() || last.get().getType().contains(IncidentType.AFTER_INCIDENT)) {
            return new ArrayList<>();
        }

        return List.of(IncidentType.AFTER_INCIDENT);
    }

    private List<IncidentType> detectMetadataChangeIncident(AnalysisRequest current, AnalysisRequest previous) {
        var result = new ArrayList<IncidentType>();

        if (!previous.deviceInfo().equals(current.deviceInfo())) {
            result.add(IncidentType.DEVICE_CHANGE);
        }

        if (!previous.ipInfo().equals(current.ipInfo())) {
            result.add(IncidentType.LOCATION_CHANGE);
        }

        return result;
    }

    private List<IncidentType> detectWhenUserExceedsLimit(AnalysisRequest request) {
        var from = Timestamp.valueOf(LocalDateTime.now().minusDays(1));
        var incidents = incidentRepository.incidentsFromDateForUser(request.user(), from);

        return incidents.size() > 10 ? List.of(IncidentType.SUSPICIOUS_USER) : List.of();
    }

    private List<IncidentType> detectWhenCountryExceedsLimit(AnalysisRequest request) {
        var from = Timestamp.valueOf(LocalDateTime.now().minusDays(1));
        var incidents = incidentRepository.incidentsFromDateForCountry(request.ipInfo().getCountryName(), from);

        return incidents.size() > 10 ? List.of(IncidentType.SUSPICIOUS_COUNTRY) : List.of();
    }
}
