package com.zpi.infrastructure.analysis;

import com.zpi.domain.analysis.twoFactor.incident.Incident;
import com.zpi.domain.analysis.twoFactor.incident.IncidentRepository;
import com.zpi.domain.analysis.twoFactor.incident.IncidentType;
import com.zpi.domain.common.AnalysisRequest;
import com.zpi.domain.common.User;
import com.zpi.domain.report.RequestIncident;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JpaIncidentRepositoryImpl implements IncidentRepository {
    private final JpaIncidentRepo incidentRepo;
    private final JpaRequestRepo requestRepo;
    private final JpaRequestRepositoryImpl requestRepository;
    private final JpaUserRepo userRepo;

    Logger logger = LoggerFactory.getLogger(JpaIncidentRepositoryImpl.class);

    @Override
    public void save(Incident incident, AnalysisRequest request) {
        requestRepository.save(request);
        var requestTuple = requestRepo.findByDatetime(request.date());
        incidentRepo.save(new IncidentTuple(incident, requestTuple.orElseThrow()));
        logger.info(
                incident.getType().stream().map(IncidentType::getName).collect(Collectors.joining(" "))
        );
    }

    @Override
    public Optional<Incident> lastIncident(User user) {
        var userTuple = userRepo.findByUsername(user.username());
        if (userTuple.isEmpty()) {
            return Optional.empty();
        }

        var last = requestRepo.findByOrderByIdDesc()
                .filter(r -> r.getUser().equals(userTuple.get()))
                .stream()
                .findFirst();

        if (last.isEmpty()) {
            return Optional.empty();
        }

        return incidentRepo.findByRequest(last.get()).map(IncidentTuple::toDomain);
    }

    @Override
    public List<RequestIncident> incidentsFromDateForUser(User user, Date date) {
        return incidentRepo
                .findAll()
                .stream()
                .filter(i -> i.getRequest().getDatetime().after(date))
                .filter(i -> i.getRequest().getUser().getUsername().equals(user.username()))
                .map(i -> new RequestIncident(
                        i.getRequest().getDatetime(),
                        IncidentTuple.toDomain(i),
                        i.getRequest().getIpInfo().toDomain(),
                        i.getRequest().getDeviceInfo().toDomain())
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestIncident> incidents(User user) {
        return incidentRepo
                .findAll()
                .stream()
                .filter(i -> i.getRequest().getUser().getUsername().equals(user.username()))
                .map(i -> new RequestIncident(
                        i.getRequest().getDatetime(),
                        IncidentTuple.toDomain(i),
                        i.getRequest().getIpInfo().toDomain(),
                        i.getRequest().getDeviceInfo().toDomain())
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestIncident> incidentsFromDateForCountry(String countryName, Date date) {
        return incidentRepo
                .findAll()
                .stream()
                .filter(i -> i.getRequest().getDatetime().after(date))
                .filter(i -> i.getRequest().getIpInfo().getCountryName().equals(countryName))
                .map(i -> new RequestIncident(
                        i.getRequest().getDatetime(),
                        IncidentTuple.toDomain(i),
                        i.getRequest().getIpInfo().toDomain(),
                        i.getRequest().getDeviceInfo().toDomain())
                )
                .collect(Collectors.toList());
    }

    public interface JpaIncidentRepo extends JpaRepository<IncidentTuple, Long> {
        Optional<IncidentTuple> findByRequest(RequestTuple request);
    }

    public interface JpaRequestRepo extends JpaRepository<RequestTuple, Long> {
        Streamable<RequestTuple> findByOrderByIdDesc();

        Optional<RequestTuple> findByDatetime(Date date);
    }

    public interface JpaUserRepo extends JpaRepository<UserTuple, Long> {
        Optional<UserTuple> findByUsername(String username);
    }
}
