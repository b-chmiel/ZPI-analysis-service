package com.zpi.infrastructure.analysis

import com.zpi.JpaConfig
import com.zpi.domain.analysis.twoFactor.Incident
import com.zpi.domain.analysis.twoFactor.IncidentRepository
import com.zpi.domain.analysis.twoFactor.IncidentType
import com.zpi.domain.analysis.twoFactor.RequestRepository
import com.zpi.testUtils.CommonFixtures
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.support.AnnotationConfigContextLoader
import spock.lang.Specification

import javax.transaction.Transactional

@ActiveProfiles("test")
@Transactional
@ContextConfiguration(
        classes = JpaConfig.class,
        loader = AnnotationConfigContextLoader.class
)
@DataJpaTest
class IncidentJpaFT extends Specification {
    @Autowired
    private IncidentRepository incidentRepository

    @Autowired
    private RequestRepository requestRepository

    def "should return false when no incidents happened"() {
        given:
            var request = CommonFixtures.analysisRequestDTO()

        when:
            var result = incidentRepository.isLastRequestIncident(request.toDomain().user())

        then:
            !result
    }

    def "should return true when recent incident took place"() {
        given:
            var request = CommonFixtures.analysisRequestDTO().toDomain()
            var incident = CommonFixtures.incident()

        when:
            incidentRepository.save(incident, request)
            var result = incidentRepository.isLastRequestIncident(request.user())

        then:
            result
    }

    def "should return false when recent incident was AFTER_INCIDENT"() {
        given:
            var request = CommonFixtures.analysisRequestDTO().toDomain()
            var incident = new Incident(List.of(IncidentType.AFTER_INCIDENT))

        when:
            incidentRepository.save(incident, request)
            var result = incidentRepository.isLastRequestIncident(request.user())

        then:
            !result
    }

    def "should return false when no incident was, but previously request was saved"() {
        given:
            var request = CommonFixtures.analysisRequestDTO().toDomain()

        when:
            requestRepository.save(request)
            var result = incidentRepository.isLastRequestIncident(request.user())

        then:
            !result
    }
}