package com.abhibus.StepDefinitions;

import java.io.IOException;
import java.util.Properties;

import com.abhibus.Factory.GenericFunctions;
import com.abhibus.Factory.Reporter;
import io.cucumber.java.*;
import org.openqa.selenium.WebDriver;

public class Hooks extends  GenericFunctions{
	static WebDriver driver;
	static Properties p;
	@BeforeAll
	public static void startReport() {
		Reporter.startResult();// <- initialize ExtentReports first
	}

	@Before
	public void startTest(Scenario scenario) throws IOException {
		Reporter.startTestCase(scenario.getName(), scenario.getStatus().toString());
		p = GenericFunctions.getProperties(); // calling the properties method
		driver = GenericFunctions.getInstance().createDriver(); // calling the initializeBrowser

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
		GenericFunctions.getInstance().closeAllBrowsers();
		Reporter.endResult();
	}
}

