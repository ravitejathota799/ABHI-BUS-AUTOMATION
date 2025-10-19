package com.abhibus.StepDefinitions;

import java.io.IOException;
import java.util.Properties;

import com.abhibus.Factory.Reporter;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.abhibus.Factory.WebDriverManager;
public class Hooks extends  WebDriverManager{
	static WebDriver driver;
	static Properties p;
	@BeforeAll
	public static void startReport() {
		Reporter.startResult();// <- initialize ExtentReports first
	}

	@Before
	public void startTest(Scenario scenario) throws IOException {
		Reporter.startTestCase(scenario.getName(), scenario.getStatus().toString());
		p = WebDriverManager.getProperties(); // calling the properties method
		driver = WebDriverManager.getInstance().createDriver(); // calling the initializeBrowser

	}

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

	@AfterAll
	public static void tearDownReport() throws IOException {
		WebDriverManager.getInstance().quitDriver();
		Reporter.endResult();
	}
}

