package com.zpi.api.report;

import com.zpi.domain.common.DeviceInfo;
import lombok.Getter;

@Getter
class DeviceInfoResponseDTO {
    private final String fingerprint;
    private final String userAgent;
    private final String browser;
    private final String engine;
    private final String engineVersion;
    private final String OS;
    private final String OSVersion;
    private final String device;
    private final String deviceType;
    private final String deviceVendor;
    private final String CPU;
    private final String colorDepth;
    private final String currentResolution;
    private final String availableResolution;
    private final String mimeTypes;
    private final String timeZone;
    private final String language;
    private final String systemLanguage;

    DeviceInfoResponseDTO(DeviceInfo info) {
        this.fingerprint = info.fingerprint();
        this.userAgent = info.userAgent();
        this.browser = info.browser();
        this.engine = info.engine();
        this.engineVersion = info.engineVersion();
        this.OS = info.OS();
        this.OSVersion = info.OSVersion();
        this.device = info.device();
        this.deviceType = info.deviceType();
        this.deviceVendor = info.deviceVendor();
        this.CPU = info.CPU();
        this.colorDepth = info.colorDepth();
        this.currentResolution = info.currentResolution();
        this.availableResolution = info.availableResolution();
        this.mimeTypes = info.mimeTypes();
        this.timeZone = info.timeZone();
        this.language = info.language();
        this.systemLanguage = info.systemLanguage();
    }
}
