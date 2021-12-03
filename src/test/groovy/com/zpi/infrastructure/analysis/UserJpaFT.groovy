package com.zpi.infrastructure.analysis

import com.zpi.JpaConfig
import com.zpi.domain.analysis.twoFactor.RequestRepository
import com.zpi.domain.common.User
import com.zpi.domain.common.UserRepository
import com.zpi.testUtils.CommonFixtures
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.support.AnnotationConfigContextLoader
import spock.lang.Specification

import java.sql.Timestamp
import java.time.LocalDateTime


@ActiveProfiles("test")
@ContextConfiguration(
        classes = JpaConfig.class,
        loader = AnnotationConfigContextLoader.class
)
@DataJpaTest
class UserJpaFT extends Specification {
    @Autowired
    private UserRepository userRepository

    @Autowired
    private RequestRepository requestRepository

    def "should return empty lockout for non existing user"() {
        given:
            def user = new User("", null)

        when:
            def result = userRepository.getLockout(user)

        then:
            result.isEmpty()
    }

    def "should apply not apply lockout on non existing user"() {
        given:
            def user = new User("", null)

        when:
            userRepository.applyLockout(user, LocalDateTime.now())

        then:
            userRepository.getLockout(user).isEmpty()
    }

    def "should increment failed attempts"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()

        when:
            requestRepository.save(request)
            userRepository.incrementFailedAttempts(request.user())
            userRepository.incrementFailedAttempts(request.user())
            def result = userRepository.incrementFailedAttempts(request.user())

        then:
            result == 3
    }

    def "should reset failed attempts"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()

        when:
            requestRepository.save(request)
            userRepository.incrementFailedAttempts(request.user())
            userRepository.incrementFailedAttempts(request.user())
            userRepository.incrementFailedAttempts(request.user())
            userRepository.resetFailedAttempts(request.user())
            def result = userRepository.incrementFailedAttempts(request.user())

        then:
            result == 1
    }

    def "should apply lockout"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()
            def date = LocalDateTime.now()

        when:
            requestRepository.save(request)
            userRepository.applyLockout(request.user(), date)

        then:
            userRepository.getLockout(request.user()).get() == Timestamp.valueOf(date)
    }
}
