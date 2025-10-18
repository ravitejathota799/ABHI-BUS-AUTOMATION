package com.TestRunner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/test/resources/Feature/BusBooking.feature", }, glue = "com.abhibus.StepDefinitions", plugin = {
        "pretty", "rerun:target/rerun.txt",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        "html:Cucumber-reports/report.html", }, monochrome = true, publish = true)
public class TestRunner_Junit {
}
