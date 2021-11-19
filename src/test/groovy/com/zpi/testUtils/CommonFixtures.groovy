package com.zpi.testUtils

import com.zpi.api.common.AnalysisRequestDTO
import com.zpi.api.common.DeviceInfoDTO
import com.zpi.api.common.IpInfoDTO
import com.zpi.api.common.UserDTO
import com.zpi.domain.analysis.twoFactor.incident.Incident
import com.zpi.domain.analysis.twoFactor.incident.IncidentType

class CommonFixtures {
    static AnalysisRequestDTO analysisRequestDTO() {
        var device = new DeviceInfoDTO("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", false, "", false, false, false, "", "", "", false, "")
        var ip = new IpInfoDTO("", "", "", "", "", "", "")
        var user = new UserDTO("")
        return new AnalysisRequestDTO(ip, device, user)
    }

    static AnalysisRequestDTO analysisRequestDTO(String username, String fingerprint) {
        var device = new DeviceInfoDTO(fingerprint, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", false, "", false, false, false, "", "", "", false, "")
        var ip = new IpInfoDTO("", "", "", "", "", "", "")
        var user = new UserDTO(username)
        return new AnalysisRequestDTO(ip, device, user)
    }

    static AnalysisRequestDTO analysisRequestDTO(String username, String deviceFingerprint, String ipInfoCity) {
        var device = new DeviceInfoDTO(deviceFingerprint, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", false, "", false, false, false, "", "", "", false, "")
        var ip = new IpInfoDTO(ipInfoCity, "", "", "", "", "", "")
        var user = new UserDTO(username)
        return new AnalysisRequestDTO(ip, device, user)
    }

    static Incident incident() {
        return new Incident(List.of(IncidentType.DEVICE_CHANGE))
    }
}
