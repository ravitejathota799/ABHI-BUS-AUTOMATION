package com.abhibus.Factory;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import com.abhibus.Components.FunctionBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class GenericFunctions extends Reporter implements FunctionBase {
    static Properties p;    // creating an instance to Properties file
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();        //creating an instance to thread local of type webdriver
    public static String remote_url = "http://192.168.0.117:4444";        //assigning the remote localhost url
    public static Logger logger; // creating an instance to logger
    WebDriver driver;    //declaring an driver

    //method for creating a driver in local or remote
    public WebDriver createDriver() throws IOException {
        if (getProperties().getProperty("execution_env").equalsIgnoreCase("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            // os
            // capturing browser name from config file
            if (getProperties().getProperty("os").equalsIgnoreCase("windows")) {
                capabilities.setPlatform(Platform.WIN11);
            } else if (getProperties().getProperty("os").equalsIgnoreCase("mac")) {
                capabilities.setPlatform(Platform.MAC);
            } else {
                System.out.println("No matching OS..");
            }
            switch (getProperties().getProperty("browser")) {
                case "chrome":
                    capabilities.setBrowserName("chrome");    // creating an instance for the chrome driver
                    break;
                case "edge":
                    capabilities.setBrowserName("MicrosoftEdge");    // creating an instance for the edge driver
                    break;
                case "firefox":
                    capabilities.setBrowserName("FireFox");        // creating an instance for the firefox driver
                default:
                    System.out.println("No matching browser");        // printing if browser doesnt matches with any of the
            }

            driver = new RemoteWebDriver(new URL(remote_url), capabilities);
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
            driver.get(p.getProperty("appURL")); // creating an instance for the remote webdriver mentioned browsers in config file
            reportStep("The" + " " + getProperties().getProperty("browser") + " " + "browser has launched successfully", "INFO");

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
            reportStep("The" + " " + getProperties().getProperty("browser") + " " + "browser has launched successfully", "INFO");
        }
        driverThreadLocal.set(driver);
        return driverThreadLocal.get(); // returning the driver
    }

    //method for returning the driver
    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    //method for returning the web driver manager instance
    public static GenericFunctions getInstance() {
        return new GenericFunctions();
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

    public static String[] getElementValue(String xpath) {
        if (xpath.contains("!")) {
            return xpath.split("!");
        } else {
            return null;
        }
    }


    @Override
    public void clickByXpath(String xpath) {
        String[] ele = getElementValue(xpath);
        try {
            GenericFunctions.getInstance().getDriver().findElement(By.xpath(ele[1])).click();
            reportStep("The element" + " " + ele[0] + " " + "is clicked successfully", "PASS");
        } catch (Exception e) {
            reportStep("Couldn't click on the element" + " " + ele[0], "FAIL");
            closeAllBrowsers();
            Assert.fail();
        }
    }

    @Override
    public void clickById(String id) {
        String[] ele = getElementValue(id);
        try {
            assert ele != null;
            GenericFunctions.getInstance().getDriver().findElement(By.id(ele[1])).click();
            reportStep("The element" + " " + ele[0] + " " + "is clicked successfully", "PASS");
        } catch (Exception e) {
            reportStep("Couldn't click on the element" + " " + ele[0], "FAIL");
            closeAllBrowsers();
            Assert.fail();
        }
    }

    @Override
    public void clickByCssSelector(String cssSelector) {
        String[] ele = getElementValue(cssSelector);
        try {
            assert ele != null;
            GenericFunctions.getInstance().getDriver().findElement(By.cssSelector(ele[1])).click();
            reportStep("The element" + " " + ele[0] + " " + "is clicked successfully", "PASS");
        } catch (Exception e) {
            reportStep("Couldn't click on the element" + " " + ele[0], "FAIL");
            closeAllBrowsers();
            Assert.fail();
        }
    }

    @Override
    public void verifyTextByXpath(String xpath, String data) {
        String[] ele = getElementValue(xpath);
        assert ele != null;
        String text = getTextByXpath(ele[1]);
        if (text.equalsIgnoreCase(data)) {
            reportStep("The values" + " -> " + data + " & " + text + "are same", "PASS");
        } else {
            reportStep("The values" + " -> " + data + " & " + text + "aren't same", "FAIL");
            closeAllBrowsers();
            Assert.fail();
        }
    }

    public String getTextByXpath(String xpath) {
        String[] ele = getElementValue(xpath);
        String bReturn = "";
        try {
            bReturn = GenericFunctions.getInstance().getDriver().findElement(By.xpath(ele[1])).getText();
            reportStep("The element" + " -> " + xpath + " is found", "INFO");
            reportStep("The text is" + " -> " + bReturn, "INFO");
        } catch (Exception e) {
            reportStep("The element" + " " + xpath + " is not found", "FAIL");
            closeAllBrowsers();
            Assert.fail();
        }
        return bReturn;
    }

    public String getTextByXpath_Attribute(String xpath,String attribute) {
        String bReturn = "";
        try {
            bReturn = GenericFunctions.getInstance().getDriver().findElement(By.xpath(xpath)).getAttribute(attribute);
            reportStep("The element" + " -> " + xpath + " is found", "INFO");
            reportStep("The text is" + " -> " + bReturn, "INFO");
        } catch (Exception e) {
            reportStep("The element" + " " + xpath + " is not found", "FAIL");
            closeAllBrowsers();
            Assert.fail();
        }
        return bReturn;
    }


    @Override
    public void enterDataByXpath(String xpath, String data) {
        try {
            GenericFunctions.getInstance().getDriver().findElement(By.xpath(xpath)).sendKeys(data);
            reportStep("The data->" + " " + data + " " + "is entered successfully", "PASS");
        } catch (Exception e) {
            reportStep("The data->" + " " + data + " " + "is not entered", "FAIL");
            e.printStackTrace();
            closeAllBrowsers();
            Assert.fail();
        }
    }

    public void waitForEle(String xpath) {
        try {
            WebDriverWait wait = new WebDriverWait(GenericFunctions.getInstance().getDriver(), Duration.ofSeconds(60));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            reportStep("Waiting for element", "INFO");
        } catch (Exception e) {
            reportStep("Couldn't find the element", "FAIL");
            e.printStackTrace();
            closeAllBrowsers();
            Assert.fail();
        }
    }

    public static int getRandomNumber(int n) {
        int randomNumber = (int) (Math.random() * n) + 1;
        System.out.println("Random number: " + randomNumber);
        return randomNumber;
    }

    public void enterValues(String xpath, String partialCity, String city) throws InterruptedException {
        String []ele = getElementValue(xpath);
        try{
            WebElement source = GenericFunctions.getInstance().getDriver().findElement(By.xpath(ele[1]));
            source.clear();
            source.sendKeys(partialCity);
            Thread.sleep(2000); // Replace with explicit wait for better reliability
            List<WebElement> srcOptions = GenericFunctions.getInstance().getDriver().findElements(By.xpath("//li[contains(@id,'aci-option-')]"));
            srcOptions.stream().filter(webElement -> webElement.getText().toString().contains(city)).findFirst().ifPresent(WebElement::click);
            reportStep("Data got entered in the element successfully", "PASS");
        }catch (Exception e) {
            reportStep("Data isn't entered in the element successfully", "FAIL");
            e.printStackTrace();
            closeAllBrowsers();
            Assert.fail();
        }

    }

    @Override
    public void closeAllBrowsers() {
//        WebDriver driver = driverThreadLocal.get();
        try {
            GenericFunctions.getInstance().getDriver().quit();
            reportStep("The Browsers closed successfully", "INFO");
        } catch (Exception e) {
            e.printStackTrace();
            closeAllBrowsers();
            Assert.fail();
        }
    }
}
