package com.zpi.api.analysis.dto;

import com.zpi.domain.analysis.response.LoginFailedResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginFailedDTO {
    private String loginAction;
    private String delayTill;

    public LoginFailedDTO(LoginFailedResponse response) {
        loginAction = response.action().toString();
        delayTill = convertDate(response.delayTill());
    }

    private String convertDate(Date date) {
        return new Timestamp(date.getTime()).toLocalDateTime().toString();
    }
}
