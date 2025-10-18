package com.abhibus.StepDefinitions;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.abhibus.Factory.WebDriverManager;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks extends WebDriverManager{

	static WebDriver driver;
	static Properties p;

	// method for setting up the browser

	@Before
	public static void setup() throws Throwable {
		p = WebDriverManager.getProperties(); // calling the properties method
		driver = WebDriverManager.getInstance().createDriver(); // calling the initializeBrowser

	}
	// method for closing the browser
	@After
	public static void tearDown() {
		WebDriverManager.getInstance().quitDriver(); // closing the browser
	}
	// method for attaching screenshot
	@AfterStep
	public void addScreenshot(Scenario scenario) throws IOException {
		// this is for cucumber junit report
//		captureScreen(scenario.getName());
//		if (!scenario.isFailed()) { // if scenario is passed
//			TakesScreenshot ts = (TakesScreenshot) driver; // taking screenshot using takingScreenshot interfacess
//			byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
//			scenario.attach(screenshot, "image/png", scenario.getName()); // attaching the screen shot
//		}
	}
}