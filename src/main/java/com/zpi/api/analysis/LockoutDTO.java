package com.zpi.api.analysis;

import com.zpi.domain.common.Lockout;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
class LockoutDTO {
    private String loginAction;
    private String delayTill;

    LockoutDTO(Lockout lockout) {
        loginAction = lockout.mode().toString();
        delayTill = convertDate(lockout.delayTill());
    }

    private String convertDate(Date date) {
        return new Timestamp(date.getTime()).toLocalDateTime().toString();
    }
}
