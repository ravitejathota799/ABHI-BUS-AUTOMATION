package com.abhibus.PageObjects;


import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.abhibus.Factory.Reporter;
import com.abhibus.Factory.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import static com.abhibus.Factory.Reporter.reportStep;


//@Listeners(com.abhibus.Factory.class)
public class Locators extends BasePage {
    //constructor for web driver
    public Locators(WebDriver driver) {
        super(driver);
    }
    private static final Logger log = LogManager.getLogger(Locators.class);
    public static String loginLink = "//*[@id=\"login-link\"]";
    public static String loginInput = "//input[@inputmode='tel']";
    public static String loginButton = "//*[@id=\"login-validation\"]/button";
	public static String busLink = "//*[@id='bus-link']";
    public static String leavingFrom = "(//input[contains(@placeholder,'Leaving From')])[1]";
    public static String goingTo = "//input[@placeholder='Going To']";
    public static String arrowIcon = "//span[contains(@class,'calender-month-change')]";
    public static String searchButton = "//*[@id=\"search-button\"]/a";
    public static String busTypeFilters = "//div[@id='seat-filter-bus-type']/a";
    public static String seatFilterDeparture = "//*[@id=\"seat-filter-departure-list\"]/a";
    public static String datePicker = "//div[@class='container date ']/a";
    public static String onwardJourneyDate = "//input[contains(@placeholder,'Onward Journey Date')]";
    public static String busPartnerList = "//div[@id='list-filter-option-container']//div[contains(@class,'primary')]/div/label";
    public static String busList = "//*[@id=\"service-cards-container\"]/div/div/div";
    public static String priceSortingIcon = "//*[@id=\"sorting-action\"]/div/div[2]/div[1]/div/a";
    public static String seatSelection = "//*[text()=\"Select Seats\"]";
    public static String errorMessage = "//*[@id=\"many-filters-container\"]/div[1]/span[1]";
    public static String viewMoreBuses = "(//span[normalize-space()='View Buses'])[1]";
    public static String availableSeat = "(//table[@id='seat-layout-details']/tbody/tr/td/div//button[@class='seat'])";
    public static String boardingLocationsAndDroppingLocations = "(//*[@id=\"place-container\"]/div)[1]";
    public static String proceedButton = "//button[text()='Proceed']";
    public static String skipLink = "//*[@id=\"login-with-google\"]/a";
    public static String passengerContact = "//*[@id=\"passenger-details-mob-input\"]/div/div[1]/div[2]/input";
    public static String passengerEmail = "//*[@id=\"passenger-details-email\"]/div/div[1]/div[2]/input";
    public static String passengerName = "//*[@id=\"passenger-detail-name\"]/div/div/div/div/div/input";
    public static String passengerAge = "//*[@id=\"passenger-detail-age\"]/div/div[1]/div/input";
    public static String refundSection = "(//span[normalize-space()=\"No, I don't want this\"])[1]";
    public static String fareDetailsDropdown = "//div[@id='fare-details-title']//div[contains(@class,'row')]";
    public static String busFare = "//div[@id='fare-details-content']/div/div[1]";
    public static String basPartnerGST = "//div[@id='fare-details-content']//div[2]";
    public static String busPartnerDiscount = "//*[@id=\"fare-details-card\"]/div/div[2]/div[1]";
    public static String totalFare = "//div[@id='fare-details-net-paid']";
    public void clickOnBusLink() {
        try{
            driver.findElement(By.xpath(busLink)).click();
            reportStep("User Navigate to abhibus website successfully","INFO");
            reportStep("User clicked on bus link successfully","INFO");
        } catch (Exception e) {
            e.fillInStackTrace();
            Assert.fail("Couldn't click on bus link");
        }
    }
    public void enterCities(){
        try{
            enterValues(leavingFrom,"Hydera","Hyderabad");
            enterValues(goingTo,"Vijaya","Vijayawada");
        } catch (Exception e) {
            e.fillInStackTrace();
            Assert.fail("Couldn't enter values");
        }
    }

    private void enterValues(String xpath,String partialCity,String city) throws InterruptedException {
        WebElement source = driver.findElement(By.xpath(xpath));
        source.clear();
        source.sendKeys(partialCity);
        Thread.sleep(2000); // Replace with explicit wait for better reliability
        List<WebElement> srcOptions = driver.findElements(By.xpath("//li[contains(@id,'aci-option-')]"));
        for(WebElement option : srcOptions) {
            if(option.getText().contains(city)) {
                option.click();
                break;
            }
        }
        reportStep("User entered city"+" "+city,"PASS");
    }

    public void generateDate(){
        try{
            // Step 1: Generate random date between now and 4 months from now
            LocalDate start = LocalDate.now();
            LocalDate end = start.plusMonths(4);
            long days = ChronoUnit.DAYS.between(start, end);
            Random rand = new Random();
            long randomDay = (long)(rand.nextDouble() * (days + 1));
            LocalDate targetDate = start.plusDays(randomDay);
            int day = targetDate.getDayOfMonth();
            // Step 2: Loop until calendar matches target month/year
            driver.findElement(By.xpath(onwardJourneyDate)).click();
            Thread.sleep(10000);
            while(true){
                if(targetDate.isAfter(start)){
                    driver.findElement(By.xpath(arrowIcon)).click();
                    break;
                }
            }
            datePicker+="[text()='"+day+"']";
            driver.findElement(By.xpath(datePicker)).click();
            reportStep("User entered date"+" "+targetDate,"PASS");
        } catch (Exception e) {
            e.getMessage();
            Assert.fail("Couldn't enter date");
            reportStep("Couldn't enter date","FAIL");
        }
    }
    public void clickOnSearchLink(){
        try{
            driver.findElement(By.xpath(searchButton)).click();
            reportStep("User Clicked on search link","PASS");
        } catch (Exception e) {
            e.fillInStackTrace();
            Assert.fail("Couldn't click on search button");
            reportStep("User Couldn't Clicked on search link","FAIL");
        }
    }

    public void applyFilters(){
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30)) // Maximum wait time
                .pollingEvery(Duration.ofSeconds(5)) // Polling interval
                .ignoring(NoSuchElementException.class); // Exception to ignore during polling
        // Use Fluent Wait to wait for an element
        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.xpath(busTypeFilters)); // Replace with your locator
            }
        });
        System.out.println("Element found: " + element.getText());
        try{
//            Ac Filter
            filtersApply(busTypeFilters, "AC");
            WebDriverManager.getInstance().getLogger().info("AC Filter applied successfully");
            Thread.sleep(1000);
            filtersApply(busTypeFilters,"Sleeper");
            WebDriverManager.getInstance().getLogger().info("Sleeper Filter applied successfully");
            Thread.sleep(1000);
            filtersApply(seatFilterDeparture,"5 PM - 11 PM");
            WebDriverManager.getInstance().getLogger().info("Departure Filter applied successfully");
            Thread.sleep(1000);
            String status = driver.findElement(By.xpath("//*[@id=\"list-filter-container\"]/div/div[1]/div[1]")).getAttribute("class");
            System.out.println("status"+" "+status);
            if(!status.contains("active")){
                driver.findElement(By.xpath("//*[@id=\"list-filter-container\"]/div/div[1]/div[1]")).click();
                List<WebElement> busPartnerName = driver.findElements(By.xpath(busPartnerList));
                String name = busPartnerName.get(getRandomNumber(busPartnerName.size())).getText();
                if(name.equalsIgnoreCase("apsrtc")||name.equalsIgnoreCase("tgsrtc")){
                    driver.findElement(By.xpath(viewMoreBuses)).click();
                }
                String xpath = busPartnerList+"[text()='"+name+"']";
                driver.findElement(By.xpath(xpath)).click();
                WebDriverManager.getInstance().getLogger().info("clicked on bus partner name");
            }else{
                List<WebElement> busPartnerName = driver.findElements(By.xpath(busPartnerList));
                String name = busPartnerName.get(getRandomNumber(busPartnerName.size())).getText();
                String xpath = busPartnerList+"[text()='"+name+"']";
                driver.findElement(By.xpath(xpath)).click();
                WebDriverManager.getInstance().getLogger().info("clicked on bus partner name");
            }
//            price sorting
            driver.findElement(By.xpath(priceSortingIcon)).click();

        }catch (Exception e){
         e.fillInStackTrace();
         Assert.fail("Couldn't apply filters");
        }
    }



    public void filtersApply(String xpath, String type){
        List<WebElement> text = driver.findElements(By.xpath(xpath));
        xpath+="/span[text()='"+type+"']";
        for(WebElement opt:text){
            if(opt.getText().equalsIgnoreCase(type)){
                driver.findElement(By.xpath(xpath)).click();
            }
        }
        reportStep("User applied filters","PASS");
    }



    public void login() {
        try{
            driver.findElement(By.xpath(loginLink)).click();
            reportStep("Clicked on login link","PASS");
            driver.findElement(By.xpath(loginInput)).sendKeys("7995769470");
            reportStep("Entered mobile number","PASS");
            driver.findElement(By.xpath(loginButton)).click();
            reportStep("Clicked on login button","PASS");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
            Thread.sleep(20000);
        } catch (Exception e) {
            e.fillInStackTrace();
            log.error("Couldn't complete above operations");
        }
    }

    public void selectSeatAndProceedForBooking() throws InterruptedException {
        Thread.sleep(10000);
        try{
            List<WebElement> elements = driver.findElements(By.xpath(errorMessage));
            if (elements.size() == 1) {
                System.out.println("Error Message"+" "+driver.findElement(By.xpath(errorMessage)).getText());
                reportStep(driver.findElement(By.xpath(errorMessage)).getText(),"INFO");
                Assert.fail("Couldn't complete");
            } else {
                int count = driver.findElements(By.xpath(busList)).size();
                System.out.println("count and index"+" "+count+" "+getRandomNumber(count));
                List<WebElement> webElements = driver.findElements(By.xpath(seatSelection));
                Thread.sleep(1000);
                String id = webElements.get(0).getAttribute("id");
                String xpath = "(//button[@id='"+id+"'])[1]";
                System.out.println("id before"+xpath);
                xpath = "//button[@id='"+id+"']";
                System.out.println("id after"+xpath);
                driver.findElement(By.xpath(xpath)).click();
                Thread.sleep(10000);
                int availableSeats = driver.findElements(By.xpath("//*[@id=\"seat-layout-details\"]/tbody/tr/td/div/button/span")).size();
                System.out.println("AVAILABLE SEATS"+availableSeats);
                System.out.println("available seats"+" "+availableSeat+"["+getRandomNumber(availableSeats)+"]");
                driver.findElement(By.xpath(availableSeat+"["+getRandomNumber(availableSeats)+"]")).click();
                driver.findElement(By.xpath(boardingLocationsAndDroppingLocations)).click();
                Thread.sleep(10000);
                Thread.sleep(10000);
                driver.findElement(By.xpath(boardingLocationsAndDroppingLocations)).click();
                driver.findElement(By.xpath(proceedButton)).click();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error("Couldn't complete Booking");
            Assert.fail("Couldn't complete booking");
        }
    }
    public static int getRandomNumber(int size){
        return new Random().nextInt(size);
    }

    public void getFareDetails() throws InterruptedException {
        Thread.sleep(10000);
        try{
            driver.findElement(By.xpath(skipLink)).click();
            driver.findElement(By.xpath(passengerContact)).sendKeys("7995769470");
            driver.findElement(By.xpath(passengerEmail)).sendKeys("ravitejathota158@gmail.com");
            driver.findElement(By.xpath(passengerName)).sendKeys("Thota Ravi Teja");
            driver.findElement(By.xpath(passengerAge)).sendKeys("24");
            driver.findElement(By.xpath(refundSection)).click();
            driver.findElement(By.xpath(fareDetailsDropdown)).click();
            System.out.println(driver.findElement(By.xpath(fareDetailsDropdown)).getText());
            System.out.println(driver.findElement(By.xpath(busFare)).getText());
            System.out.println(driver.findElement(By.xpath(basPartnerGST)).getText());
            System.out.println(driver.findElement(By.xpath(busPartnerDiscount)).getText());
            System.out.println(driver.findElement(By.xpath(totalFare)).getText());
        }catch (Exception e){
            Assert.fail("Couldn't get fare details");
        }
    }
}
