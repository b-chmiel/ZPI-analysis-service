package com.zpi.infrastructure.analysis;

import com.zpi.domain.analysis.twoFactor.Incident;
import com.zpi.domain.analysis.twoFactor.IncidentRepository;
import com.zpi.domain.common.AnalysisRequest;
import com.zpi.domain.common.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
public class JpaIncidentRepositoryImpl implements IncidentRepository {
    private final JpaIncidentRepo incidentRepo;
    private final JpaRequestRepo requestRepo;
    private final JpaRequestRepositoryImpl requestRepository;
    private final JpaUserRepo userRepo;

    @Override
    public void save(Incident incident, AnalysisRequest request) {
        requestRepository.save(request);
        var requestTuple = requestRepo.findByDatetime(request.date());
        incidentRepo.save(new IncidentTuple(incident, requestTuple.orElseThrow()));
    }

    @Override
    public boolean isLastRequestIncident(User user) {
        var userTuple = userRepo.findByUsername(user.username());
        if (userTuple.isEmpty()) {
            return false;
        }

        var last = requestRepo.findByOrderByIdDesc()
                .filter(r -> r.getUser().equals(userTuple.get()))
                .stream()
                .findFirst();

        return last.filter(
                requestTuple -> incidentRepo
                        .findByRequest(requestTuple)
                        .stream()
                        .findFirst()
                        .filter(e -> !e.getType().contains(IncidentTypeTuple.AFTER_INCIDENT)).isPresent()
        ).isPresent();
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
