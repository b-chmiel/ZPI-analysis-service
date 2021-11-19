package com.zpi.domain.analysis


import com.zpi.domain.analysis.lockoutService.LockoutService
import com.zpi.domain.analysis.response.LoginAction
import com.zpi.domain.analysis.response.LoginFailedResponse
import com.zpi.domain.analysis.response.TwoFactorResponse
import com.zpi.domain.analysis.twoFactor.TwoFactorService
import com.zpi.testUtils.CommonFixtures
import spock.lang.Specification
import spock.lang.Subject

class AnalysisServiceUT extends Specification {
    def twoFactor = Mock(TwoFactorService)
    def lockout = Mock(LockoutService)

    @Subject
    def service = new AnalysisServiceImpl(twoFactor, lockout)

    def "should return response"() {
        given:
            def request = CommonFixtures.analysisRequestDTO().toDomain()
            def loginFailedResponse = new LoginFailedResponse(LoginAction.ALLOW, new Date())
            def twoFactorResponse = new TwoFactorResponse(false)

        and:
            twoFactor.evaluate(request) >> twoFactorResponse
            lockout.evaluate(request) >> loginFailedResponse

        when:
            def result = service.analyse(request)

        then:
            result.twoFactor() == twoFactorResponse
            result.loginFailed().action() == loginFailedResponse.action()
            result.loginFailed().delayTill() == loginFailedResponse.delayTill()
    }
}
