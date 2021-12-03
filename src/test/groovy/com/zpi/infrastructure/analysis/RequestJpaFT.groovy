package com.zpi.infrastructure.analysis

import com.zpi.JpaConfig
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
class RequestJpaFT extends Specification {
    @Autowired
    private RequestRepository requestRepository

    def "should retrieve newest request for user"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()
            requestRepository.save(request)

        when:
            var result = requestRepository.retrieveLastEntry(request.user())

        then:
            result.get().date() == request.date()
            result.get().deviceInfo() == request.deviceInfo()
            result.get().ipInfo() == request.ipInfo()
            result.get().user().username() == request.user().username()
    }

    def "should retrieve newest request for user multiple requests"() {
        given:
            def request = CommonFixtures.analysisRequestDTO("a", "f").toDomain()
            requestRepository.save(CommonFixtures.analysisRequestDTO("a", "a").toDomain())
            requestRepository.save(CommonFixtures.analysisRequestDTO("a", "c").toDomain())
            requestRepository.save(CommonFixtures.analysisRequestDTO("b", "a").toDomain())
            requestRepository.save(CommonFixtures.analysisRequestDTO("d", "b").toDomain())
            requestRepository.save(CommonFixtures.analysisRequestDTO("a", "c").toDomain())
            requestRepository.save(CommonFixtures.analysisRequestDTO("a", "c").toDomain())
            requestRepository.save(request)
            requestRepository.save(CommonFixtures.analysisRequestDTO("d", "d").toDomain())

        when:
            var result = requestRepository.retrieveLastEntry(request.user())

        then:
            result.get().date() == request.date()
            result.get().deviceInfo() == request.deviceInfo()
            result.get().ipInfo() == request.ipInfo()
            result.get().user().username() == request.user().username()
    }
}
