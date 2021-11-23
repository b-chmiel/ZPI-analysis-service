package com.zpi.domain.common;

import java.util.Date;

public record Lockout(LockoutMode mode, Date delayTill) {
}
