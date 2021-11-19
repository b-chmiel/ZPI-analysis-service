package com.zpi.domain.analysis.twoFactor

import com.zpi.domain.analysis.twoFactor.incident.Incident
import com.zpi.domain.analysis.twoFactor.incident.IncidentDetectionService
import com.zpi.domain.analysis.twoFactor.incident.IncidentRepository
import com.zpi.domain.analysis.twoFactor.incident.IncidentType
import com.zpi.testUtils.CommonFixtures
import spock.lang.Specification
import spock.lang.Subject

class TwoFactorServiceUT extends Specification {
    def requestRepository = Mock(RequestRepository)
    def incidentRepository = Mock(IncidentRepository)
    def incidentDetection = Mock(IncidentDetectionService)

    @Subject
    def service = new TwoFactorServiceImpl(requestRepository, incidentRepository, incidentDetection)

    def "should save analysis data when no incident detected"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()

        and:
            incidentDetection.detect(request) >> Optional.empty()

        when:
            def result = service.evaluate(request)

        then:
            !result.isAdditionalLayerRequired()

        and:
            1 * requestRepository.save(request)
            0 * incidentRepository.save(_, _)
    }

    def "should save incident when incident detected"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()
            def incident = new Incident(List.of(IncidentType.PASSWORD_INCORRECT))

        and:
            incidentDetection.detect(request) >> Optional.of(incident)

        when:
            def result = service.evaluate(request)

        then:
            result.isAdditionalLayerRequired()

        and:
            1 * incidentRepository.save(incident, request)
            0 * requestRepository.save(_)
    }

}
