package com.appium.config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Ejecución en BrowserStack App Automate.
 * Requiere: BROWSERSTACK_USERNAME y BROWSERSTACK_ACCESS_KEY (env o -D),
 * y app=bs://... o custom_id (hash o alias obtenido al subir el APK).
 */
public class BrowserStackDriverProvider implements DriverProvider {
    static final String HUB = "https://hub.browserstack.com/wd/hub";

    @Override
    public AppiumDriver create(TestConfig config) throws Exception {
        String user = config.getBsUser();
        String key = config.getBsKey();
        if (user == null || user.isBlank() || key == null || key.isBlank()) {
            throw new IllegalStateException(
                "Faltan credenciales BrowserStack. Define BROWSERSTACK_USERNAME y "
                + "BROWSERSTACK_ACCESS_KEY como variables de entorno o "
                + "-Dbrowserstack.user=... -Dbrowserstack.key=...");
        }

        String app = config.getApp();
        if (app == null || app.isBlank() || app.equals("bs://PON_AQUI_EL_HASH")) {
            throw new IllegalStateException(
                "En BrowserStack 'app' debe ser bs://... o tu custom_id. Sube el APK con:\n"
                + "curl -u \"$BROWSERSTACK_USERNAME:$BROWSERSTACK_ACCESS_KEY\" "
                + "-X POST https://api-cloud.browserstack.com/app-automate/upload "
                + "-F file=@calculator-base.apk -F custom_id=CalculatorApp\n"
                + "y usa -Dapp=bs://<hash> o -Dapp=CalculatorApp");
        }

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(config.getPlatformName())
                .setAutomationName(config.getAutomationName())
                .setDeviceName(config.getDeviceName())
                .setApp(app)
                .setAutoGrantPermissions(config.getAutoGrantPermissions())
                .setNoReset(config.getNoReset())
                .setNewCommandTimeout(Duration.ofSeconds(config.getNewCommandTimeoutSec()));

        if (!config.getOsVersion().isBlank()) {
            options.setCapability("appium:osVersion", config.getOsVersion());
        }

        Map<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("userName", user);
        bstackOptions.put("accessKey", key);
        bstackOptions.put("projectName", config.getBsProject());
        bstackOptions.put("buildName", config.getBsBuild());
        bstackOptions.put("sessionName", config.getBsSession());
        bstackOptions.put("appiumVersion", config.getBsAppiumVersion());
        bstackOptions.put("debug", true);
        bstackOptions.put("networkLogs", true);
        options.setCapability("bstack:options", bstackOptions);

        String hub = config.getHubUrl();
        if (hub == null || hub.isBlank() || hub.contains("localhost")
                || !hub.contains("browserstack.com") || !hub.contains("/wd/hub")) {
            if (hub != null && !hub.isBlank() && !hub.contains("localhost")) {
                System.out.println("[BrowserStack] hub.url inválido '" + hub
                        + "', usando " + HUB);
            }
            hub = HUB;
        }

        AppiumDriver driver = new AndroidDriver(new URL(hub), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWaitSec()));
        return driver;
    }
}
