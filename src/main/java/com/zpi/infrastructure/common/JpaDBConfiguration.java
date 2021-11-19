package com.zpi.infrastructure.common;

import com.zpi.infrastructure.analysis.JpaIncidentRepositoryImpl;
import com.zpi.infrastructure.analysis.JpaRequestRepositoryImpl;
import com.zpi.infrastructure.analysis.JpaUserRepositoryImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {JpaRequestRepositoryImpl.class, JpaIncidentRepositoryImpl.class, JpaUserRepositoryImpl.class}, considerNestedRepositories = true)
@EntityScan(basePackageClasses = {JpaRequestRepositoryImpl.class, JpaIncidentRepositoryImpl.class, JpaUserRepositoryImpl.class})
public class JpaDBConfiguration {
    @Bean
    JpaRequestRepositoryImpl requestRepository(JpaRequestRepositoryImpl.JpaUserRepo user,
                                               JpaRequestRepositoryImpl.JpaIpInfoRepo ipInfo,
                                               JpaRequestRepositoryImpl.JpaDeviceInfoRepo deviceInfo,
                                               JpaRequestRepositoryImpl.JpaRequestRepo requestRepo
    ) {
        return new JpaRequestRepositoryImpl(user, ipInfo, deviceInfo, requestRepo);
    }

    @Bean
    JpaIncidentRepositoryImpl incidentRepository(JpaIncidentRepositoryImpl.JpaIncidentRepo jpa,
                                                 JpaIncidentRepositoryImpl.JpaRequestRepo requestRepoJpa,
                                                 JpaRequestRepositoryImpl requestRepo,
                                                 JpaIncidentRepositoryImpl.JpaUserRepo userRepo) {
        return new JpaIncidentRepositoryImpl(jpa, requestRepoJpa, requestRepo, userRepo);
    }

    @Bean
    JpaUserRepositoryImpl userRepository(JpaUserRepositoryImpl.JpaUserRepo jpa) {
        return new JpaUserRepositoryImpl(jpa);
    }
}
