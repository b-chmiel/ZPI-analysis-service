package com.zpi.domain.analysis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class LoginFailedResponse {
    private final LoginAction action;
    private final LocalDateTime delayTill;
}
