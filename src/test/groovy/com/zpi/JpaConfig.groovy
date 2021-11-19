package com.zpi

import com.zpi.infrastructure.analysis.JpaIncidentRepositoryImpl
import com.zpi.infrastructure.analysis.JpaRequestRepositoryImpl
import com.zpi.infrastructure.analysis.JpaUserRepositoryImpl
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.EnableTransactionManagement

import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = [JpaRequestRepositoryImpl.class, JpaIncidentRepositoryImpl.class, JpaUserRepositoryImpl.class], considerNestedRepositories = true)
@EntityScan(basePackageClasses = [JpaRequestRepositoryImpl.class, JpaIncidentRepositoryImpl.class, JpaUserRepositoryImpl.class])
@ActiveProfiles("test")
class JpaConfig {
    @Value("{spring.datasource.url}")
    private String url

    @Value("{spring.datasource.driverClassName}")
    private String driver

    @Value("{spring.datasource.username}")
    private String username

    @Value("{spring.datasource.password}")
    private String password

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource()
        dataSource.setDriverClassName(driver)
        dataSource.setUrl(url)
        dataSource.setUsername(username)
        dataSource.setPassword(password)

        return dataSource;
    }

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
