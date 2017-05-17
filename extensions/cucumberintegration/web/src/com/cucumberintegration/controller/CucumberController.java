package com.cucumberintegration.controller;

import cucumber.runtime.Runtime;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.cucumberintegration.runtime.CucumberRuntimeBuilder;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import de.hybris.platform.servicelayer.config.ConfigurationService;


@Controller
public class CucumberController {
    private static final Logger LOG = LoggerFactory.getLogger(CucumberController.class);

    private static final String CUCUMBER_FEATURES_DIRECTORY = "cucumber.features";
    private static final String CUCUMBER_REPORT_DIRECTORY = "cucumber.report";

    @Resource
    private ConfigurationService configurationService;

    @RequestMapping("/**")
    public ResponseEntity<String> run(HttpServletRequest request) throws IOException {
        Runtime runtime = new CucumberRuntimeBuilder()
                .withFeatureRootDirectory(configurationService.getConfiguration().getString(CUCUMBER_FEATURES_DIRECTORY))
                .withFeaturePath(extractFeaturePathFromRequest(request))
                .withStepPackage("com.cucumberintegration.steps")
                .withHtmlReport(new File(getTemporaryDirectory(), "html").getAbsolutePath())
                .withJsonReport(new File(getTemporaryDirectory(), "report.json").getAbsolutePath())
                .withTextReport(new File(getTemporaryDirectory(), "report.txt").getAbsolutePath())
                .withJunitReport(new File(getTemporaryDirectory(), "report.xml").getAbsolutePath())
                .build();
        runtime.run();

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    private String getTemporaryDirectory() {
        return configurationService.getConfiguration().getString(CUCUMBER_REPORT_DIRECTORY);
    }

    private String extractFeaturePathFromRequest(final HttpServletRequest request) {
        return ((String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)).substring(1);
    }
}
