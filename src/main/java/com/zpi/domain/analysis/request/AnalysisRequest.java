package com.zpi.domain.analysis.request;

public record AnalysisRequest(IpInfo ipInfo,
                              DeviceInfo deviceInfo,
                              User user) {
}
