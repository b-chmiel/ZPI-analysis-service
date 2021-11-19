package com.zpi.domain.failedLogin

import com.zpi.domain.analysis.twoFactor.Incident
import com.zpi.domain.analysis.twoFactor.IncidentRepository
import com.zpi.domain.analysis.twoFactor.IncidentType
import com.zpi.domain.common.AnalysisRequest
import com.zpi.domain.common.UserRepository
import com.zpi.testUtils.CommonFixtures
import spock.lang.Specification
import spock.lang.Subject

class FailedLoginUT extends Specification {
    def incidentRepository = Mock(IncidentRepository)
    def userRepository = Mock(UserRepository)

    @Subject
    def service = new FailedLoginServiceImpl(incidentRepository, userRepository)

    def "should report lockout incident when user has more than 2 fail attempts"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()
            userRepository.incrementFailedAttempts(request.user()) >> 3

        when:
            service.report(request)

        then:
            1 * incidentRepository.save(_, _) >> {
                Incident incident, AnalysisRequest req ->
                    assert incident.getType().get(0) == IncidentType.LOCKOUT
                    assert req == request
            }

            1 * userRepository.applyLockout(request.user(), _)
    }

    def "should report password_incorrect incident when user has less than 3 fail attempts"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()
            userRepository.incrementFailedAttempts(request.user()) >> 2

        when:
            service.report(request)

        then:
            1 * incidentRepository.save(_, _) >> {
                Incident incident, AnalysisRequest req ->
                    assert incident.getType().get(0) == IncidentType.PASSWORD_INCORRECT
                    assert req == request
            }

            0 * userRepository.applyLockout(_, _)
    }
}
