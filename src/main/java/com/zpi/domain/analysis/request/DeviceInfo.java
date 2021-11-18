package com.zpi.domain.analysis.request;

public record DeviceInfo(
        String fingerprint,
        String userAgent,
        String browser,
        String engine,
        String engineVersion,
        String OS,
        String OSVersion,
        String device,
        String deviceType,
        String deviceVendor,
        String CPU,
        String screenPrint,
        String colorDepth,
        String currentResolution,
        String availableResolution,
        String mimeTypes,
        String fonts,
        String timeZone,
        String language,
        String systemLanguage
) {
}