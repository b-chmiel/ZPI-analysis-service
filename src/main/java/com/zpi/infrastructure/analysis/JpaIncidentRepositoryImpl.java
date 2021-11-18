package com.zpi.infrastructure.analysis;

import com.zpi.domain.analysis.request.AnalysisRequest;
import com.zpi.domain.analysis.twoFactor.Incident;
import com.zpi.domain.analysis.twoFactor.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public class JpaIncidentRepositoryImpl implements IncidentRepository {
    private final JpaIncidentRepo repo;

    @Override
    public void save(Incident incident, AnalysisRequest request) {
        repo.save(new IncidentTuple(incident, request));

    }

    public interface JpaIncidentRepo extends JpaRepository<IncidentTuple, Long> {
    }
}
