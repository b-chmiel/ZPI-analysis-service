package com.zpi.domain.analysis

import com.zpi.domain.analysis.lockout.LockoutService
import com.zpi.domain.analysis.twoFactor.TwoFactorService
import com.zpi.domain.common.Lockout
import com.zpi.domain.common.LockoutMode
import com.zpi.testUtils.CommonFixtures
import spock.lang.Specification
import spock.lang.Subject

class AnalysisServiceUT extends Specification {
    def twoFactorService = Mock(TwoFactorService)
    def lockoutService = Mock(LockoutService)

    @Subject
    def service = new AnalysisServiceImpl(twoFactorService, lockoutService)

    def "should return response"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()
            def lockout = new Lockout(LockoutMode.ALLOW, new Date())
            def twoFactorResponse = new TwoFactorResponse(false)

        and:
            twoFactorService.evaluate(request) >> twoFactorResponse
            lockoutService.evaluate(request.user()) >> lockout

        when:
            def result = service.analyse(request)

        then:
            result.twoFactor() == twoFactorResponse
            result.lockout().mode() == lockout.mode()
            result.lockout().delayTill() == lockout.delayTill()
    }
}
