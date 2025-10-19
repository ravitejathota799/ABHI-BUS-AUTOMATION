package com.abhibus.PageObjects;


import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

import com.abhibus.Factory.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static com.abhibus.Factory.Reporter.*;


//@Listeners(com.abhibus.Factory.class)
public class Locators extends BasePage {
    //constructor for web driver
    public Locators(WebDriver driver) {
        super(driver);
    }
    private static final Logger log = LogManager.getLogger(Locators.class);
    public static String loginLink = "loginLink!//*[@id=\"login-link\"]";
    public static String loginInput = "loginInput!//input[@inputmode='tel']";
    public static String loginButton = "loginButton!//*[@id=\"login-validation\"]/button";
	public static String busLink = "busLink!//*[@id='bus-link']";
    public static String leavingFrom = "leavingFrom!(//input[contains(@placeholder,'Leaving From')])[1]";
    public static String goingTo = "goingTo!//input[@placeholder='Going To']";
    public static String arrowIcon = "CalendarArrowIcon!//span[contains(@class,'calender-month-change')]";
    public static String searchButton = "searchButton!//*[@id=\"search-button\"]/a";
    public static String busTypeFilters = "busTypeFilters!//div[@id='seat-filter-bus-type']/a";
    public static String seatFilterDeparture = "seatFilterDeparture!//*[@id=\"seat-filter-departure-list\"]/a";
    public static String datePicker = "datePicker!//div[@class='container date ']/a";
    public static String onwardJourneyDate = "onwardJourneyDate!//input[contains(@placeholder,'Onward Journey Date')]";
    public static String busPartnerList = "busPartnerList!//div[@id='list-filter-option-container']//div[contains(@class,'primary')]/div/label";
    public static String busList = "busList!//*[@id=\"service-cards-container\"]/div/div/div";
    public static String priceSortingIcon = "priceSortingIcon!//*[@id=\"sorting-action\"]/div/div[2]/div[1]/div/a";
    public static String seatSelection = "seatSelection!//*[text()=\"Select Seats\"]";
    public static String errorMessage = "errorMessage!//*[@id=\"many-filters-container\"]/div[1]/span[1]";
    public static String viewMoreBuses = "viewMoreBuses!(//span[normalize-space()='View Buses'])[1]";
    public static String availableSeat = "availableSeat!(//*[@id=\"seat-layout-details\"]/tbody/tr/td/div/button/span)[1]";
    public static String boardingLocationsAndDroppingLocations = "boardingLocationsAndDroppingLocations!(//*[@id=\"place-container\"]/div)[1]";
    public static String proceedButton = "proceedButton!//button[text()='Proceed']";
    public static String skipLink = "skipLink!//*[@id=\"login-with-google\"]/a";
    public static String passengerContact = "passengerContact!//*[@id=\"passenger-details-mob-input\"]/div/div[1]/div[2]/input";
    public static String passengerEmail = "passengerEmail!//*[@id=\"passenger-details-email\"]/div/div[1]/div[2]/input";
    public static String passengerName = "passengerName!//*[@id=\"passenger-detail-name\"]/div/div/div/div/div/input";
    public static String passengerAge = "passengerAge!//*[@id=\"passenger-detail-age\"]/div/div[1]/div/input";
    public static String refundSection = "refundSection!(//span[normalize-space()=\"No, I don't want this\"])[1]";
    public static String fareDetailsDropdown = "fareDetailsDropdown!//div[@id='fare-details-title']//div[contains(@class,'row')]";
    public static String busFare = "busFare!//div[@id='fare-details-content']/div/div[1]";
    public static String basPartnerGST = "basPartnerGST!//div[@id='fare-details-content']//div[2]";
    public static String busPartnerDiscount = "busPartnerDiscount!//*[@id=\"fare-details-card\"]/div/div[2]/div[1]";
    public static String totalFare = "totalFare!//div[@id='fare-details-net-paid']";

    public static String[] getElementValue(String xpath){
        if(xpath.contains("!")){
            return xpath.split("!");
        }else{
            return null;
        }
    }

    public void clickOnBusLink() {
        String[] elementValue = getElementValue(busLink);
        reportStep("Chrome Browser launched successfully","INFO");
        try{
            reportStep("User Navigated to abhibus website successfully","INFO");
            reportStep("User clicked on "+ Objects.requireNonNull(elementValue)[0]+" successfully","PASS");
            driver.findElement(By.xpath(elementValue[1])).click();
        } catch (Exception e) {
            e.fillInStackTrace();
            reportStep("Couldn't click on bus link","FAIL");
        }
    }
    public void enterCities(){
        String[] source = getElementValue(leavingFrom);
        String[] destination = getElementValue(goingTo);
        try{
            enterValues(source[1],"Hydera","Hyderabad");
            reportStep("User entered source city in "+source[0]+" ","PASS");
            enterValues(destination[1],"Vijaya","Vijayawada");
            reportStep("User entered destination city in "+destination[0]+" ","PASS");
        } catch (Exception e) {
            e.fillInStackTrace();
            reportStep("Couldn't enter values in the fields","FAIL");
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
        String[] elementValue = getElementValue(onwardJourneyDate);
        String[] date = getElementValue(datePicker);
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
            assert elementValue != null;
            driver.findElement(By.xpath(elementValue[1])).click();
            Thread.sleep(10000);
            while(true){
                if(targetDate.isAfter(start)){
                    driver.findElement(By.xpath(Objects.requireNonNull(getElementValue(arrowIcon))[1])).click();
                    break;
                }
            }
            date[1]+="[text()='"+day+"']";
            driver.findElement(By.xpath(date[1])).click();
            reportStep("User entered date"+" "+targetDate,"PASS");
        } catch (Exception e) {
            e.getMessage();
            Assert.fail("Couldn't enter date");
            reportStep("Couldn't enter date","FAIL");
        }
    }
    public void clickOnSearchLink(){
        String[] ele = getElementValue(searchButton);
        try{
            driver.findElement(By.xpath(Objects.requireNonNull(ele)[1])).click();
            reportStep("User Clicked on "+ele[0]+" link","PASS");
        } catch (Exception e) {
            e.fillInStackTrace();
            Assert.fail("Couldn't click on search button");
            reportStep("User Couldn't Clicked on "+ele[0]+" link","FAIL");
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
                return driver.findElement(By.xpath(Objects.requireNonNull(getElementValue(busTypeFilters))[1])); // Replace with your locator
            }
        });
        reportStep("Element found: " + element.getText(),"INFO");
        try{
//            Ac Filter
            filtersApply(busTypeFilters, "AC");
            Thread.sleep(10000);
            filtersApply(busTypeFilters,"Sleeper");
            Thread.sleep(10000);
            filtersApply(seatFilterDeparture,"5 PM - 11 PM");
            Thread.sleep(10000);
            String status = driver.findElement(By.xpath("//*[@id=\"list-filter-container\"]/div/div[1]/div[1]")).getAttribute("class");
            reportStep("status of dropdown filter"+" "+status,"INFO");
            if(!status.contains("active")){
                driver.findElement(By.xpath("//*[@id=\"list-filter-container\"]/div/div[1]/div[1]")).click();
                List<WebElement> busPartnerName = driver.findElements(By.xpath(Objects.requireNonNull(getElementValue(busPartnerList))[1]));
                String name = busPartnerName.get(getRandomNumber(busPartnerName.size())).getText();
                if(name.equalsIgnoreCase("apsrtc")||name.equalsIgnoreCase("tgsrtc")){
                    driver.findElement(By.xpath(Objects.requireNonNull(getElementValue(viewMoreBuses))[1])).click();
                    reportStep("Element found for apstrct or tsrtc" + Objects.requireNonNull(getElementValue(viewMoreBuses))[0],"INFO");
                }
                String xpath = Objects.requireNonNull(getElementValue(busPartnerList))[1]+"[text()='"+name+"']";
                driver.findElement(By.xpath(xpath)).click();
                reportStep("clicked on bus partner name","PASS");
            }else{
                List<WebElement> busPartnerName = driver.findElements(By.xpath(Objects.requireNonNull(getElementValue(busPartnerList))[1]));
                String name = busPartnerName.get(getRandomNumber(busPartnerName.size())).getText();
                String xpath = Objects.requireNonNull(getElementValue(busPartnerList))[1]+"[text()='"+name+"']";
                driver.findElement(By.xpath(xpath)).click();
                reportStep("clicked on '"+ Objects.requireNonNull(getElementValue(busPartnerList))[0]+"'","PASS");
            }
//            price sorting
            driver.findElement(By.xpath(Objects.requireNonNull(getElementValue(priceSortingIcon))[1])).click();
            reportStep("clicked on '"+ Objects.requireNonNull(getElementValue(priceSortingIcon))[0]+"'","PASS");


        }catch (Exception e){
         e.fillInStackTrace();
         reportStep("Couldn't apply filters","FAIL");
         Assert.fail("Couldn't apply filters");
        }
    }



    public void filtersApply(String xpath, String type){
        String[] element = getElementValue(xpath);
        List<WebElement> text = driver.findElements(By.xpath(Objects.requireNonNull(element)[1]));
        element[1]+="/span[text()='"+type+"']";
        for(WebElement opt:text){
            if(opt.getText().equalsIgnoreCase(type)){
                driver.findElement(By.xpath(element[1])).click();
                reportStep("User applied filters for"+" "+element[0]+" "+type,"PASS");
            }
        }
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
        String[] errorMsg = getElementValue(errorMessage);
        try{
            List<WebElement> elements = driver.findElements(By.xpath(Objects.requireNonNull(errorMsg)[1]));
            if (elements.size() == 1) {
                System.out.println("Error Message"+" "+driver.findElement(By.xpath(errorMsg[1])).getText());
                reportStep(driver.findElement(By.xpath(errorMsg[1])).getText(),"INFO");
                Assert.fail("Couldn't complete");
            } else {
                int count = driver.findElements(By.xpath(Objects.requireNonNull(getElementValue(busList))[1])).size();
                reportStep("total no.of buses and index"+" "+count+" "+getRandomNumber(count),"INFO");
                List<WebElement> webElements = driver.findElements(By.xpath(Objects.requireNonNull(getElementValue(seatSelection))[1]));
                Thread.sleep(1000);
                String id = webElements.get(0).getAttribute("id");
                String xpath = "(//button[@id='"+id+"'])[1]";
                System.out.println("id before"+xpath);
                xpath = "//button[@id='"+id+"']";
                System.out.println("id after"+xpath);
                driver.findElement(By.xpath(xpath)).click();
                Thread.sleep(10000);
                int availableSeats = driver.findElements(By.xpath("//*[@id=\"seat-layout-details\"]/tbody/tr/td/div/button/span")).size();
                reportStep("AVAILABLE SEATS"+" "+availableSeats,"INFO");
                int idx = getRandomNumber(availableSeats);
                Thread.sleep(30000);
                String [] seatAvailability = getElementValue(availableSeat);
                System.out.println("seat selection"+seatAvailability[1]);
                driver.findElement(By.xpath(Objects.requireNonNull(seatAvailability)[1])).click();
                reportStep("seat selected with index"+ " "+seatAvailability[0],"INFO");
                Thread.sleep(10000);
                String[] boardingDroppingLocations = getElementValue(boardingLocationsAndDroppingLocations);
                driver.findElement(By.xpath(boardingDroppingLocations[1])).click();
                Thread.sleep(10000);
                driver.findElement(By.xpath(boardingDroppingLocations[1])).click();
                driver.findElement(By.xpath(Objects.requireNonNull(getElementValue(proceedButton))[1])).click();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error("Couldn't complete Booking");
            Assert.fail("Couldn't complete booking");
            reportStep("Couldn't complete booking","FAIL");
        }
    }
    public static int getRandomNumber(int n){
        int randomNumber = (int)(Math.random() * n) + 1;
        System.out.println("Random number: " + randomNumber);
        return  randomNumber;
    }

    public void getFareDetails() throws InterruptedException {
//        Thread.sleep(1000);
//        new WebDriverWait(Duration.ofMinutes(1), ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login-with-google\"]/a")));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login-with-google\"]/a")));
        reportStep("Element you're looking for "+ Objects.requireNonNull(getElementValue(skipLink))[0]+ "is found","INFO");
        try{
            driver.findElement(By.xpath(Objects.requireNonNull(getElementValue(skipLink))[1])).click();
            reportStep("User clicked on "+ Objects.requireNonNull(getElementValue(skipLink))[0]+" link","PASS");
            driver.findElement(By.xpath(Objects.requireNonNull(getElementValue(passengerContact))[1])).sendKeys("7995769470");
            reportStep("User entered "+ Objects.requireNonNull(getElementValue(passengerContact))[0]+" number","PASS");
            driver.findElement(By.xpath(Objects.requireNonNull(getElementValue(passengerEmail))[1])).sendKeys("ravitejathota158@gmail.com");
            reportStep("User entered "+ Objects.requireNonNull(getElementValue(passengerEmail))[0]+" id","PASS");
            driver.findElement(By.xpath(Objects.requireNonNull(getElementValue(passengerName))[1])).sendKeys("Thota Ravi Teja");
            reportStep("User entered "+ Objects.requireNonNull(getElementValue(passengerName))[0],"PASS");
            driver.findElement(By.xpath(Objects.requireNonNull(getElementValue(passengerAge))[1])).sendKeys("24");
            reportStep("User entered "+ Objects.requireNonNull(getElementValue(passengerAge))[0],"PASS");
            driver.findElement(By.xpath(Objects.requireNonNull(getElementValue(refundSection))[1])).click();
            reportStep("User clicked on "+ Objects.requireNonNull(getElementValue(refundSection))[0],"PASS");
            driver.findElement(By.xpath(Objects.requireNonNull(getElementValue(fareDetailsDropdown))[1])).click();
            reportStep("User clicked on "+ Objects.requireNonNull(getElementValue(fareDetailsDropdown))[0],"PASS");
            reportStep(driver.findElement(By.xpath(Objects.requireNonNull(getElementValue(fareDetailsDropdown))[1])).getText(),"INFO");
            reportStep(driver.findElement(By.xpath(Objects.requireNonNull(getElementValue(busFare))[1])).getText(),"INFO");
            reportStep(driver.findElement(By.xpath(Objects.requireNonNull(getElementValue(basPartnerGST))[1])).getText(),"INFO");
            reportStep(driver.findElement(By.xpath(Objects.requireNonNull(getElementValue(busPartnerDiscount))[1])).getText(),"INFO");
            reportStep(driver.findElement(By.xpath(Objects.requireNonNull(getElementValue(totalFare))[1])).getText(),"INFO");
            reportStep("Browser closed successfully","INFO");
        }catch (Exception e){
            Assert.fail("Couldn't get fare details");
            reportStep("Couldn't get fare details","FAIL");
        }
    }
}
