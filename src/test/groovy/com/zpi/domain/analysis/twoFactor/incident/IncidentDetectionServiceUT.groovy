package com.zpi.domain.analysis.twoFactor.incident

import com.zpi.domain.analysis.twoFactor.RequestRepository
import com.zpi.domain.common.UserRepository
import com.zpi.testUtils.CommonFixtures
import spock.lang.Specification
import spock.lang.Subject

class IncidentDetectionServiceUT extends Specification {
    def requestRepository = Mock(RequestRepository)
    def incidentRepository = Mock(IncidentRepository)
    def userRepository = Mock(UserRepository)
    def userIncidentsRepository = Mock(UserIncidentsRepository)
    def countryIncidentsRepository = Mock(CountryIncidentsRepository)

    @Subject
    def service = new IncidentDetectionServiceImpl(requestRepository, incidentRepository, userRepository, userIncidentsRepository, countryIncidentsRepository)

    def "should detect incident when this is first login"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()

        and:
            incidentRepository.lastIncident(request.user()) >> false
            requestRepository.retrieveLastEntry(request.user()) >> Optional.empty()

        when:
            def result = service.detect(request)

        then:
            result.get().getType() == List.of(IncidentType.FIRST_LOGIN)
            result.get().getSeverity() == IncidentSeverity.LOW
    }

    def "should not detect incident when no last incident and no device change nor location change"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()

        and:
            userRepository.getLockout(request.user()) >> Optional.empty()
            incidentRepository.lastIncident(request.user()) >> Optional.empty()
            requestRepository.retrieveLastEntry(request.user()) >> Optional.of(request)
            requestRepository.startDate() >> Optional.of(new Date())

        when:
            def result = service.detect(request)

        then:
            result.isEmpty()
    }

    def "should detect incident when last incident"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()
            def incident = new Incident(List.of(IncidentType.LOCKOUT))

        and:
            userRepository.getLockout(request.user()) >> Optional.empty()
            incidentRepository.lastIncident(request.user()) >> Optional.of(incident)
            requestRepository.retrieveLastEntry(request.user()) >> Optional.of(request)
            requestRepository.startDate() >> Optional.of(new Date())

        when:
            def result = service.detect(request)

        then:
            def expected = new Incident(List.of(IncidentType.AFTER_INCIDENT))
            result.get().getSeverity() == expected.getSeverity()
            result.get().getType() == expected.getType()
    }

    def "should not detect incident when last incident was AFTER_INCIDENT"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()
            def incident = new Incident(List.of(IncidentType.AFTER_INCIDENT))

        and:
            userRepository.getLockout(request.user()) >> Optional.empty()
            incidentRepository.lastIncident(request.user()) >> Optional.of(incident)
            requestRepository.retrieveLastEntry(request.user()) >> Optional.of(request)
            requestRepository.startDate() >> Optional.of(new Date())

        when:
            def result = service.detect(request)

        then:
            result.isEmpty()
    }

    def "should detect incident on device change"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()
            def last = CommonFixtures.analysisRequestDTO("", request.deviceInfo().fingerprint() + "fds", request.ipInfo().getCity()).toDomain()

        and:
            userRepository.getLockout(request.user()) >> Optional.empty()
            incidentRepository.lastIncident(request.user()) >> Optional.empty()
            requestRepository.retrieveLastEntry(request.user()) >> Optional.of(last)
            requestRepository.startDate() >> Optional.of(new Date())

        when:
            def result = service.detect(request)

        then:
            def expected = new Incident(List.of(IncidentType.DEVICE_CHANGE))
            result.get().getType() == expected.getType()
    }

    def "should detect incident on ip change"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()
            def last = CommonFixtures.analysisRequestDTO("", request.deviceInfo().fingerprint(), request.ipInfo().getCity() + "asdf").toDomain()

        and:
            userRepository.getLockout(request.user()) >> Optional.empty()
            incidentRepository.lastIncident(request.user()) >> Optional.empty()
            requestRepository.retrieveLastEntry(request.user()) >> Optional.of(last)
            requestRepository.startDate() >> Optional.of(new Date())

        when:
            def result = service.detect(request)

        then:
            def expected = new Incident(List.of(IncidentType.LOCATION_CHANGE))
            result.get().getType() == expected.getType()
    }

    def "should detect incident on ip and device change"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()
            def last = CommonFixtures.analysisRequestDTO("", request.deviceInfo().fingerprint() + "asdf", request.ipInfo().getCity() + "asdf").toDomain()

        and:
            userRepository.getLockout(request.user()) >> Optional.empty()
            incidentRepository.lastIncident(request.user()) >> Optional.empty()
            requestRepository.retrieveLastEntry(request.user()) >> Optional.of(last)
            requestRepository.startDate() >> Optional.of(new Date())

        when:
            def result = service.detect(request)

        then:
            result.get().getType().contains(IncidentType.LOCATION_CHANGE)
            result.get().getType().contains(IncidentType.DEVICE_CHANGE)
    }

    def "should detect incident when request was made during lockout"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()

        and:
            userRepository.getLockout(request.user()) >> Optional.of(new Date())
            incidentRepository.lastIncident(request.user()) >> Optional.empty()
            requestRepository.retrieveLastEntry(request.user()) >> Optional.of(request)
            requestRepository.startDate() >> Optional.of(new Date())

        when:
            def result = service.detect(request)

        then:
            result.get().getType().contains(IncidentType.REQUEST_DESPITE_LOCKOUT)
    }
}
