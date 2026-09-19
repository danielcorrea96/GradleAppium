package com.appium.config;

import io.appium.java_client.AppiumDriver;

/** Contrato para cada destino de ejecución. Añadir otro cloud = nueva implementación. */
public interface DriverProvider {
    AppiumDriver create(TestConfig config) throws Exception;
}
