package com.zpi.infrastructure.common;

import com.zpi.infrastructure.analysis.JpaIncidentRepositoryImpl;
import com.zpi.infrastructure.analysis.JpaRequestRepositoryImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {JpaRequestRepositoryImpl.class, JpaIncidentRepositoryImpl.class}, considerNestedRepositories = true)
@EntityScan(basePackageClasses = {JpaRequestRepositoryImpl.class, JpaIncidentRepositoryImpl.class})
public class JpaDBConfiguration {
    @Bean
    JpaRequestRepositoryImpl requestRepository(JpaRequestRepositoryImpl.JpaRequestRepo jpa) {
        return new JpaRequestRepositoryImpl(jpa);
    }

    @Bean
    JpaIncidentRepositoryImpl incidentRepository(JpaIncidentRepositoryImpl.JpaIncidentRepo jpa) {
        return new JpaIncidentRepositoryImpl(jpa);
    }
}
