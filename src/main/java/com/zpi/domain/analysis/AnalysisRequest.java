package com.zpi.domain.analysis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AnalysisRequest {
    private final IpInfo ipInfo;
    private final DeviceInfo deviceInfo;
    private final AuditUser auditUser;
}
