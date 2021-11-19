package com.zpi.api.common;

import com.zpi.domain.common.AnalysisRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class AnalysisRequestDTO {
    private final IpInfoDTO ipInfo;
    private final DeviceInfoDTO deviceInfo;
    private final UserDTO user;

    public AnalysisRequest toDomain() {
        var date = new Date();
        return new AnalysisRequest(date, ipInfo.toDomain(), deviceInfo.toDomain(), user.toDomain());
    }
}
