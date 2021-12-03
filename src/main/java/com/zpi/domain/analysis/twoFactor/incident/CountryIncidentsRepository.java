package com.zpi.domain.analysis.twoFactor.incident;

public interface CountryIncidentsRepository {
    int incidentsForCountry(String country);

    int totalIncidents();
}
