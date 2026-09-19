package com.appium.config;

/**
 * Entornos de ejecución soportados.
 * Se selecciona con -Dexecution=local|browserstack (o env EXECUTION).
 * Para añadir un nuevo proveedor (saucelabs, lambdatest...):
 * 1) añadir valor al enum, 2) crear XxxDriverProvider, 3) registrarlo en DriverFactory.
 */
public enum ExecutionEnvironment {
    LOCAL,
    BROWSERSTACK;

    public static ExecutionEnvironment from(String value) {
        if (value == null || value.isBlank()) {
            return LOCAL;
        }
        switch (value.trim().toLowerCase()) {
            case "local":
                return LOCAL;
            case "browserstack":
            case "bs":
            case "browser_stack":
                return BROWSERSTACK;
            default:
                throw new IllegalArgumentException(
                    "execution desconocido: '" + value + "'. Valores válidos: local, browserstack");
        }
    }
}
