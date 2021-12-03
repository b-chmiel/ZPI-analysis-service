package com.zpi.infrastructure.analysis.views;

import com.zpi.domain.analysis.twoFactor.incident.CountryIncidentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class JpaCountryIncidentRepositoryImpl implements CountryIncidentsRepository {
    private final JpaCountryIncidentsRepo repo;

    @Override
    public int incidentsForCountry(String country) {
        return repo.findByCountry(country).map(CountryIncidentsView::getCount).orElse(0);
    }

    @Override
    public int totalIncidents() {
        return repo.findAll().stream().mapToInt(CountryIncidentsView::getCount).sum();
    }

    public interface JpaCountryIncidentsRepo extends JpaRepository<CountryIncidentsView, Long> {
        Optional<CountryIncidentsView> findByCountry(String country);
    }
}
