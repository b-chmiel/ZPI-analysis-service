package com.zpi.api.analysis.response;

import com.zpi.domain.analysis.response.LoginFailedResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginFailedDTO {
    private String loginAction;
    private String delayTill;

    public LoginFailedDTO(LoginFailedResponse response) {
        loginAction = response.action().toString();
        delayTill = response.delayTill().toString();
    }
}
