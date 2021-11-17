package com.zpi.api.analysis;

import com.zpi.domain.analysis.AnalysisRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnalysisRequestDTO {
    private final IpInfoDTO ipInfo;
    private final DeviceInfoDTO deviceInfo;
    private final AuditUserDTO user;

    public AnalysisRequest toDomain() {
        return new AnalysisRequest(ipInfo.toDomain(), deviceInfo.toDomain(), user.toDomain());
    }
}
