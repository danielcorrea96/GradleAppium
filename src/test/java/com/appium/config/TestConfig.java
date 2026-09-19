package com.appium.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuración parametrizable del test.
 *
 * Orden de prioridad (mayor a menor):
 * 1) -Dprop (System.getProperty), ej: -DdeviceName="Samsung Galaxy S23"
 * 2) Variable de entorno (ej: DEVICE_NAME, BROWSERSTACK_USERNAME)
 * 3) src/test/resources/config/&lt;execution&gt;.properties
 * 4) default del código
 */
public class TestConfig {

    private final ExecutionEnvironment execution;
    private final Properties fileProps = new Properties();

    private TestConfig(ExecutionEnvironment execution) {
        this.execution = execution;
        String resource = "/config/" + execution.name().toLowerCase() + ".properties";
        try (InputStream in = TestConfig.class.getResourceAsStream(resource)) {
            if (in != null) {
                fileProps.load(in);
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer " + resource, e);
        }
    }

    public static TestConfig load() {
        String raw = getRaw("execution", "local");
        return new TestConfig(ExecutionEnvironment.from(raw));
    }

    public ExecutionEnvironment getExecution() {
        return execution;
    }

    // --- helpers de lectura ---

    private static String getRaw(String key, String defaultValue) {
        // 1) -Dkey
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) {
            return sys.trim();
        }
        // 2) ENV: EXECUTION, DEVICE_NAME, HUB_URL, BROWSERSTACK_USERNAME...
        String envKey = key.replaceAll("([a-z])([A-Z])", "$1_$2")
                .replace('.', '_')
                .toUpperCase();
        String env = System.getenv(envKey);
        if (env != null && !env.isBlank()) {
            return env.trim();
        }
        return defaultValue;
    }

    public String get(String key, String defaultValue) {
        String raw = getRaw(key, null);
        if (raw != null) {
            return raw;
        }
        String file = fileProps.getProperty(key);
        if (file != null && !file.isBlank()) {
            return file.trim();
        }
        return defaultValue;
    }

    // --- getters tipados ---

    public String getHubUrl() {
        return get("hub.url", "http://localhost:4723");
    }

    public String getPlatformName() {
        return get("platformName", "Android");
    }

    public String getAutomationName() {
        return get("automationName", "UiAutomator2");
    }

    public String getDeviceName() {
        return get("deviceName", "Medium_Phone_API_36.0");
    }

    public String getOsVersion() {
        return get("osVersion", "");
    }

    /** Puede ser ruta local .apk o bs://... en BrowserStack. */
    public String getApp() {
        return get("app", "");
    }

    public String getAppPackage() {
        return get("appPackage", "com.google.android.calculator");
    }

    public String getAppActivity() {
        return get("appActivity", "com.android.calculator2.Calculator");
    }

    public boolean getNoReset() {
        return Boolean.parseBoolean(get("noReset", "false"));
    }

    public boolean getAutoGrantPermissions() {
        return Boolean.parseBoolean(get("autoGrantPermissions", "true"));
    }

    public long getNewCommandTimeoutSec() {
        return Long.parseLong(get("newCommandTimeout", "300"));
    }

    public long getImplicitWaitSec() {
        return Long.parseLong(get("implicitWait", "15"));
    }

    // --- opciones BrowserStack (bstack:options) ---

    public String getBsUser() {
        return getRaw("browserstack.user", System.getenv("BROWSERSTACK_USERNAME"));
    }

    public String getBsKey() {
        return getRaw("browserstack.key", System.getenv("BROWSERSTACK_ACCESS_KEY"));
    }

    public String getBsProject() {
        return get("bs.projectName", "GradleAppium");
    }

    public String getBsBuild() {
        return get("bs.buildName", "calc-build-local");
    }

    public String getBsSession() {
        return get("bs.sessionName", "Calculator sum test");
    }

    public String getBsAppiumVersion() {
        return get("bs.appiumVersion", "2.19.0");
    }
}
