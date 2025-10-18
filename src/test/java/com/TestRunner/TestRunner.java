package com.TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.testng.annotations.Test;

@RunWith(Cucumber.class)
@CucumberOptions(

        features = {
                "src/test/resources/Feature/BusBooking.feature", }, glue = "com.abhibus.StepDefinitions", plugin = {
        "pretty", "rerun:target/rerun.txt",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        "html:Cucumber-reports/report.html", }, monochrome = true, publish = true)
@Test
public class TestRunner extends AbstractTestNGCucumberTests {

}
