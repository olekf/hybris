package com.cucumberintegration;

import cucumber.api.java.ObjectFactory;

import java.util.HashMap;
import java.util.Map;

import de.hybris.platform.core.Registry;

public class CucumberObjectFactory implements ObjectFactory {

    private Map<Class<?>, Object> cache;

    @Override
    public void start() {
        cache = new HashMap<>();
    }

    @Override
    public void stop() {
        cache = null;
    }

    @Override
    public boolean addClass(final Class<?> aClass) {
        return true;
    }

    @Override
    public <T> T getInstance(final Class<T> aClass) {
        return aClass.cast(cache.computeIfAbsent(aClass, Registry.getCoreApplicationContext()::getBean));
    }
}
