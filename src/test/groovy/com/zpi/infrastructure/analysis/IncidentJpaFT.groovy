package com.zpi.infrastructure.analysis

import com.zpi.JpaConfig
import com.zpi.domain.analysis.twoFactor.RequestRepository
import com.zpi.domain.analysis.twoFactor.incident.IncidentRepository
import com.zpi.testUtils.CommonFixtures
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.support.AnnotationConfigContextLoader
import spock.lang.Specification

@ActiveProfiles("test")
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

    def "should return empty when no incidents happened"() {
        given:
            def request = CommonFixtures.analysisRequestDTO()

        when:
            def result = incidentRepository.lastIncident(request.toDomain().user())

        then:
            result.isEmpty()
    }

    def "should return incident when recent incident took place"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()
            def incident = CommonFixtures.incident()

        when:
            incidentRepository.save(incident, request)
            def result = incidentRepository.lastIncident(request.user())

        then:
            result.get().getType() == incident.getType()
            result.get().getSeverity() == incident.getSeverity()
    }

    def "should return empty when no incident was, but previously request was saved"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()

        when:
            requestRepository.save(request)
            def result = incidentRepository.lastIncident(request.user())

        then:
            result.isEmpty()
    }

    def "should return incident when some requests took place"() {
        given:
            def request = CommonFixtures.analysisRequestDTO()
            def incident = CommonFixtures.incident()
            def user = request.getUser().toDomain()

        when:
            requestRepository.save(request.toDomain())
            requestRepository.save(request.toDomain())
            requestRepository.save(request.toDomain())
            incidentRepository.save(incident, request.toDomain())
            var result = incidentRepository.lastIncident(user)

        then:
            result.get().getType() == incident.getType()
            result.get().getSeverity() == incident.getSeverity()
    }
}