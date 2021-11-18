package com.zpi.infrastructure.analysis;

import com.zpi.domain.analysis.request.AnalysisRequest;
import com.zpi.domain.analysis.request.User;
import com.zpi.domain.analysis.twoFactor.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class JpaRequestRepositoryImpl implements RequestRepository {
    private final JpaRequestRepo repo;

    @Override
    public void save(AnalysisRequest request) {
        repo.save(new RequestTuple(request));
    }

    @Override
    public Optional<AnalysisRequest> retrieveLastEntry(User user) {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().filter(e -> e.getUser().toDomain().equals(user)).findFirst().map(RequestTuple::toDomain);
    }

    public interface JpaRequestRepo extends JpaRepository<RequestTuple, Long> {
    }
}
