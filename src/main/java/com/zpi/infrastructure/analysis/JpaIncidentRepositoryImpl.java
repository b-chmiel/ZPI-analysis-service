package com.zpi.infrastructure.analysis;

import com.zpi.domain.analysis.twoFactor.Incident;
import com.zpi.domain.analysis.twoFactor.IncidentRepository;
import com.zpi.domain.common.AnalysisRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public class JpaIncidentRepositoryImpl implements IncidentRepository {
    private final JpaIncidentRepo repo;
    private final JpaRequestRepo requestRepo;
    private final JpaRequestRepositoryImpl requestRepository;

    @Override
    public void save(Incident incident, AnalysisRequest request) {
        requestRepository.save(request);
        var requestTuple = requestRepo.findAll().stream().filter(r -> r.getDatetime().equals(request.date())).findFirst();
        repo.save(new IncidentTuple(incident, requestTuple.orElseThrow()));
    }

    public interface JpaIncidentRepo extends JpaRepository<IncidentTuple, Long> {
    }

    public interface JpaRequestRepo extends JpaRepository<RequestTuple, Long> {
    }
}
