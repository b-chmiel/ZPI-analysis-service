package com.zpi.domain.analysis.lockout

import com.zpi.domain.analysis.response.LoginAction
import com.zpi.domain.common.UserRepository
import com.zpi.testUtils.CommonFixtures
import spock.lang.Specification
import spock.lang.Subject

import java.sql.Timestamp
import java.time.LocalDateTime

class LockoutServiceUT extends Specification {
    def repository = Mock(UserRepository)

    @Subject
    def service = new LockoutServiceImpl(repository)

    def "should return allow when no lockout"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()
            repository.getLockout(request.user()) >> Optional.empty()

        when:
            def result = service.evaluate(request)

        then:
            result.action() == LoginAction.ALLOW
    }

    def "should return allow when lockout date passed"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()
            def date = Timestamp.valueOf(LocalDateTime.now().minusDays(1))
            repository.getLockout(request.user()) >> Optional.of(date)

        when:
            def result = service.evaluate(request)

        then:
            result.action() == LoginAction.ALLOW

        and:
            1 * repository.resetFailedAttempts(request.user())
    }

    def "should block when lockout did not pass"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()
            def date = Timestamp.valueOf(LocalDateTime.now().plusDays(1))
            repository.getLockout(request.user()) >> Optional.of(date)

        when:
            def result = service.evaluate(request)

        then:
            result.action() == LoginAction.BLOCK
            result.delayTill() == date
    }
}
