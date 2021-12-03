package com.zpi.domain.analysis.twoFactor.incident;

import com.zpi.domain.analysis.twoFactor.RequestRepository;
import com.zpi.domain.common.AnalysisRequest;
import com.zpi.domain.common.User;
import com.zpi.domain.common.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IncidentDetectionServiceImpl implements IncidentDetectionService {
    private final RequestRepository requestRepository;
    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;
    private final UserIncidentsRepository userIncidentsRepository;
    private final CountryIncidentsRepository countryIncidentsRepository;

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
        var size = incidents == null ? 0 : incidents.size();
        return size > userLimit(request.user().username()) ? List.of(IncidentType.SUSPICIOUS_USER) : List.of();
    }

    private int userLimit(String username) {
        final var defaultLimit = 100;
        var dateStart = requestRepository.startDate();

        if (dateStart.isPresent()) {
            var to = LocalDateTime.now();
            int totalElapsed = getTotalElapsed(dateStart.get(), to);

            if (totalElapsed == 0) {
                return defaultLimit;
            }

            var total = userIncidentsRepository.totalIncidents();

            int limit = total / totalElapsed;

            var user = userRepository.user(username);
            if (user.isPresent()) {
                limit = (limit + getUserAvgIncidents(to, user)) / 2;
            }

            System.out.println("Limit: " + limit);
            return limit;
        }

        return defaultLimit;
    }

    private int getUserAvgIncidents(LocalDateTime to, Optional<User> user) {
        if (user.isPresent()) {
            var userId = userRepository.userId(user.get());
            if (userId.isPresent()) {
                var userElapsed = getTotalElapsed(user.get().from(), to);

                if (userElapsed == 0) {
                    return 0;
                }

                return userIncidentsRepository.incidentsForUserId(userId.get()) / userElapsed;
            }
        }

        return 0;
    }

    private List<IncidentType> detectWhenCountryExceedsLimit(AnalysisRequest request) {
        var from = Timestamp.valueOf(LocalDateTime.now().minusDays(1));
        var incidents = incidentRepository.incidentsFromDateForCountry(request.ipInfo().getCountryName(), from);

        var size = incidents == null ? 0 : incidents.size();
        return size > countryLimit(request.ipInfo().getCountryName()) ? List.of(IncidentType.SUSPICIOUS_COUNTRY) : List.of();
    }

    private int countryLimit(String country) {
        final var defaultLimit = 100;
        var dateStart = requestRepository.startDate();

        if (dateStart.isPresent()) {
            var to = LocalDateTime.now();
            int totalElapsed = getTotalElapsed(dateStart.get(), to);

            if (totalElapsed == 0) {
                return defaultLimit;
            }

            var total = countryIncidentsRepository.totalIncidents();

            int limit = total / totalElapsed;

            limit = (limit + getCountryAvgIncidents(dateStart.get(), to, country)) / 2;

            System.out.println("Limit: " + limit);
            return limit;
        }

        return defaultLimit;
    }

    private int getCountryAvgIncidents(Date from, LocalDateTime to, String country) {
        var elapsed = getTotalElapsed(from, to);

        if (elapsed == 0) {
            return 0;
        }

        return countryIncidentsRepository.incidentsForCountry(country) / elapsed;
    }

    private int getTotalElapsed(Date dateStart, LocalDateTime to) {
        var totalFrom = LocalDateTime.ofInstant(dateStart.toInstant(), ZoneId.systemDefault());
        return (int) totalFrom.until(to, ChronoUnit.MINUTES);
    }
}
