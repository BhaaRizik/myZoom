package com.bhaa.myapplication.utils.Languge;

public class DeviceProperties {
    private static String deviceLanguage;

    public static String getDeviceLanguage() {
        return deviceLanguage;
    }

    public static void setDeviceLanguage(String deviceLanguage) {
        DeviceProperties.deviceLanguage = deviceLanguage;
    }
}
