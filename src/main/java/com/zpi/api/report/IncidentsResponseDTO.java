package com.zpi.api.report;

import com.zpi.api.common.IpInfoDTO;
import com.zpi.domain.report.RequestIncident;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
final class IncidentsResponseDTO {
    private final Date date;
    private final IncidentDTO incident;
    private final IpInfoDTO ipInfo;
    private final DeviceInfoResponseDTO deviceInfo;

    IncidentsResponseDTO(RequestIncident result) {
        this.date = result.date();
        this.incident = new IncidentDTO(result.incident());
        this.ipInfo = new IpInfoDTO(result.ipInfo());
        this.deviceInfo = new DeviceInfoResponseDTO(result.deviceInfo());
    }
}
