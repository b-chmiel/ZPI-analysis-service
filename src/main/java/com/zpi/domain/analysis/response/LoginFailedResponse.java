package com.zpi.domain.analysis.response;

import java.util.Date;

public record LoginFailedResponse(LoginAction action, Date delayTill) {
}
