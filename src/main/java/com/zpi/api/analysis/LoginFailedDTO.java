package com.zpi.api.analysis;

import com.zpi.domain.analysis.LoginAction;
import com.zpi.domain.analysis.LoginFailedResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginFailedDTO {
    private String loginAction;
    private String delayTill;

    public LoginFailedDTO(LoginFailedResponse response) {
        loginAction = response.getAction().toString();
        delayTill = response.getDelayTill().toString();
    }

    public LoginFailedResponse toDomain() {
        var action = this.loginAction.equals("ALLOW") ? LoginAction.ALLOW : LoginAction.BLOCK;
        var delay = LocalDateTime.parse(this.delayTill);
        return new LoginFailedResponse(action, delay);
    }
}
