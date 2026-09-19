package com.appium.config;

import io.appium.java_client.AppiumDriver;

/** Punto único de creación del driver según -Dexecution. */
public final class DriverFactory {

    private DriverFactory() {
    }

    public static AppiumDriver createDriver() throws Exception {
        TestConfig config = TestConfig.load();
        DriverProvider provider;
        switch (config.getExecution()) {
            case BROWSERSTACK:
                provider = new BrowserStackDriverProvider();
                break;
            case LOCAL:
            default:
                provider = new LocalDriverProvider();
                break;
        }
        return provider.create(config);
    }
}
