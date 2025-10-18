	package com.abhibus.Factory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverManager {
	static Properties p;	// creating an instance to Properties file
	private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();		//creating an instance to thread local of type webdriver
	public static String remote_url = "http://localhost:4444/";		//assigning the remote localhost url
	public static Logger logger; // creating an instance to logger
	WebDriver driver;	//declaring an driver
	
	//method for creating a driver in local or remote
	public WebDriver createDriver() throws IOException {
		if (getProperties().getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			// os
			if (getProperties().getProperty("os").equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			} else if (getProperties().getProperty("os").equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			} else {
				System.out.println("No matching OS..");
			}
			// capturing browser name from config file
			switch (getProperties().getProperty("browser")) {
			case "chrome":
				capabilities.setBrowserName("chrome");	// creating an instance for the chrome driver
				break;
			case "edge":
				capabilities.setBrowserName("MicrosoftEdge");	// creating an instance for the edge driver
				break;
			case "firefox":
				capabilities.setBrowserName("FireFox");		// creating an instance for the firefox driver
			default:
				System.out.println("No matching browser");		// printing if browser doesnt matches with any of the
			}

			driver = new RemoteWebDriver(new URL(remote_url), capabilities);
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
			driver.get(p.getProperty("appURL")); // creating an instance for the remote webdriver mentioned browsers in config file

		} else if (getProperties().getProperty("execution_env").equalsIgnoreCase("local")) {
			switch (getProperties().getProperty("browser")) {
			// switch(browser.toLowerCase()){
			case "chrome":
				driver = new ChromeDriver(); // creating an instance for the chrome driver
				break;
			case "edge":
				driver = new EdgeDriver(); // creating an instance for the edge driver
				break;
			case "firefox":
				driver = new FirefoxDriver(); // creating an instance for the firefox driver
				break;

			default:
				System.out.println("#####No matching browser"); // printing if browser doesnt matches with any of the mentioned browsers in config file
				driver = null;
			}
			driver.manage().deleteAllCookies(); // deleting all the cookies
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
			driver.manage().window().maximize();
			driver.get(p.getProperty("appURL"));
		}	
		driverThreadLocal.set(driver);
		return driverThreadLocal.get(); // returning the driver
	}

	//method for quiting the driver
	public void quitDriver() {
		WebDriver driver = driverThreadLocal.get();
		if (driver != null) {
			driver.quit();
		}
	}

	//method for returning the driver
	public WebDriver getDriver() {
		return driverThreadLocal.get();
	}
	
	//method for returning the web driver manager instance
	public static WebDriverManager getInstance() {
		return new WebDriverManager();
	}

	//method for accessing properties
	public static Properties getProperties() throws IOException {
		FileReader file = new FileReader(System.getProperty("user.dir") + "/src/test/resources/config.properties"); // config file path
		p = new Properties(); // creating an object for the properties
		p.load(file); // loading properties file
		return p; // returning the properties object
	}

	//method for loggers
	public Logger getLogger() {
		logger = LogManager.getLogger(); // Log4j
		return logger;
	}
	
	//method for cpaturing screenshot
	public String captureScreen(String tname) throws IOException {
		  	WebDriver driver = WebDriverManager.getInstance().getDriver();
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());		//getting time stamp to create a new image whenever it is run
			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;	 		 //creating an instance of taking  screenshot and casting to driver type
			File source = takesScreenshot.getScreenshotAs(OutputType.FILE);	 	 //capturing the screenshot using getScreenShot method
			String destination = System.getProperty("user.dir") + "/ScreenShots/" + tname + "_" + timeStamp + ".png";	  	// storing it in the given folder in the current directory
			System.out.println(destination);	 	//printing the path 
			try {
				FileUtils.copyFile(source, new File(destination)); 		//copying the file to the given path
			} catch (Exception e) {
				e.getMessage();	 	//printing exception message
			}
			return destination; 		//returning the path 
		}
	}
