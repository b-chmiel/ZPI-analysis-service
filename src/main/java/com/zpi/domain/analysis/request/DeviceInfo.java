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
        String deviceXDPI,
        String deviceYDPI,
        String mimeTypes,
        boolean isFont,
        String fonts,
        boolean isLocalStorage,
        boolean isSessionStorage,
        boolean isCookie,
        String timeZone,
        String language,
        String systemLanguage,
        boolean isCanvas,
        String canvasPrint
) {
}