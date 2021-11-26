package com.zpi.domain.report;

import com.zpi.domain.analysis.twoFactor.incident.Incident;
import com.zpi.domain.common.DeviceInfo;
import com.zpi.domain.common.IpInfo;

import java.util.Date;

public record RequestIncident(
        Date date,
        Incident incident,
        IpInfo ipInfo,
        DeviceInfo deviceInfo
) {
}
