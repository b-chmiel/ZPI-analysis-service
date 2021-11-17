package com.zpi.domain.analysis.response;

import java.time.LocalDateTime;

public record LoginFailedResponse(LoginAction action,
                                  LocalDateTime delayTill) {
}
