package com.zpi.domain.analysis.twoFactor.incident;

public interface UserIncidentsRepository {
    int incidentsForUserId(Long id);

    int totalIncidents();
}
