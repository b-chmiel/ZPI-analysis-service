package com.zpi.infrastructure.analysis.views;

import com.zpi.domain.analysis.twoFactor.incident.UserIncidentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class JpaUserIncidentsRepositoryImpl implements UserIncidentsRepository {
    private final JpaUserIncidentsRepo repo;

    @Override
    public int incidentsForUserId(Long id) {
        return repo.findById(id).map(UserIncidentsView::getCount).orElse(0);
    }

    @Override
    public int totalIncidents() {
        return repo.findAll().stream().mapToInt(UserIncidentsView::getCount).sum();
    }

    public interface JpaUserIncidentsRepo extends JpaRepository<UserIncidentsView, Long> {
        Optional<UserIncidentsView> findById(Long id);
    }
}
