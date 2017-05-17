package com.cucumberintegration.search;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features",
        glue = "com.cucumberintegration.steps",
        plugin = {"pretty", "html:target/cucumber"})
public class SearchTest {
}
