package com.appium.config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.io.File;
import java.net.URL;
import java.time.Duration;

/** Ejecución local: emulator + Appium en localhost:4723. */
public class LocalDriverProvider implements DriverProvider {

    @Override
    public AppiumDriver create(TestConfig config) throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(config.getPlatformName())
                .setAutomationName(config.getAutomationName())
                .setDeviceName(config.getDeviceName())
                .setAutoGrantPermissions(config.getAutoGrantPermissions())
                .setNoReset(config.getNoReset())
                .setNewCommandTimeout(Duration.ofSeconds(config.getNewCommandTimeoutSec()));

        String app = config.getApp();
        if (app != null && !app.isBlank() && !app.startsWith("bs://")) {
            File apk = new File(app);
            // admite ruta relativa al proyecto (ej: calculator-base.apk)
            if (apk.exists()) {
                options.setApp(apk.getAbsolutePath());
            } else {
                options.setApp(app);
            }
        } else {
            // Calculadora preinstalada en el emulador
            options.setAppPackage(config.getAppPackage());
            options.setAppActivity(config.getAppActivity());
        }

        AppiumDriver driver = new AndroidDriver(new URL(config.getHubUrl()), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWaitSec()));
        return driver;
    }
}
