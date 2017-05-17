package com.cucumberintegration.runtime;

import cucumber.runtime.Backend;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.java.JavaBackend;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;

import com.cucumberintegration.CucumberObjectFactory;

import static java.util.Collections.singletonList;

public class CucumberRuntimeBuilder {

    private String featureRootDirectory;
    private String featurePath;
    private ArrayList<String> options = new ArrayList<>();

    public CucumberRuntimeBuilder withFeatureRootDirectory(String featureRootDirectory) {
        this.featureRootDirectory = featureRootDirectory;
        return this;
    }

    public CucumberRuntimeBuilder withFeaturePath(String featurePath) {
        this.featurePath = featurePath;
        return this;
    }

    public CucumberRuntimeBuilder withStepPackage(String stepPackage) {
        options.add("--glue");
        options.add(stepPackage);
        return this;
    }

    public CucumberRuntimeBuilder withHtmlReport(String htmlReportDirectory) {
        options.add("--plugin");
        options.add("html:" + htmlReportDirectory);
        return this;
    }

    public CucumberRuntimeBuilder withJsonReport(String jsonReportFile) {
        options.add("--plugin");
        options.add("json:" + jsonReportFile);
        return this;
    }

    public CucumberRuntimeBuilder withTextReport(String textReportFile) {
        options.add("--plugin");
        options.add("pretty:" + textReportFile);
        return this;
    }

    public CucumberRuntimeBuilder withJunitReport(String junitReportFile) {
        options.add("--plugin");
        options.add("junit:" + junitReportFile);
        return this;
    }

    public Runtime build() throws IOException {
        return new Runtime(createFeatureLoader(), getClass().getClassLoader(), createBackends(),
                createCucumberOptions());
    }


    private RuntimeOptions createCucumberOptions() {
        options.add(MultiLoader.CLASSPATH_SCHEME + featurePath);
        return new RuntimeOptions(options);
    }

    private MultiLoader createFeatureLoader() throws IOException {
        URLClassLoader urlClassLoader =
                new URLClassLoader(
                        new URL[]{new File(featureRootDirectory).getCanonicalFile().toURI().toURL()},
                        getClass().getClassLoader());
        return new MultiLoader(urlClassLoader);
    }

    private Collection<Backend> createBackends() {
        return singletonList(new JavaBackend(new CucumberObjectFactory()));
    }
}
