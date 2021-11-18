package com.zpi.domain.common;

import java.util.Date;

public record AnalysisRequest(
        Date date,
        IpInfo ipInfo,
        DeviceInfo deviceInfo,
        User user
) {
}
