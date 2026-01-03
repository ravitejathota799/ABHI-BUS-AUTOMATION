package com.abhibus.PageObjects;


import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

import com.abhibus.Factory.GenericFunctions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;


public class Locators extends GenericFunctions {
    //constructor for web driver
//    public Locators(WebDriver driver) {
//        super(driver);
//    }
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
    public static String passengerContact = "//*[@id=\"passenger-details-mob-input\"]/div/div[1]/div[2]/input";
    public static String passengerEmail = "//*[@id=\"passenger-details-email\"]/div/div[1]/div[2]/input";
    public static String passengerName = "//*[@id=\"passenger-detail-name\"]/div/div/div/div/div/input";
    public static String passengerAge = "//*[@id=\"passenger-detail-age\"]/div/div[1]/div/input";
    public static String refundSection = "refundSection!(//span[normalize-space()=\"No, I don't want this\"])[1]";
    public static String fareDetailsDropdown = "fareDetailsDropdown!//div[@id='fare-details-title']//div[contains(@class,'row')]";
    public static String busFare = "busFare!//div[@id='fare-details-content']/div/div[1]";
    public static String basPartnerGST = "basPartnerGST!//div[@id='fare-details-content']//div[2]";
    public static String busPartnerDiscount = "busPartnerDiscount!//*[@id=\"fare-details-card\"]/div/div[2]/div[1]";
    public static String totalFare = "totalFare!//div[@id='fare-details-net-paid']";

    public Locators(WebDriver driver) {
        super();
    }


    public void clickOnBusLink() throws InterruptedException {
        Thread.sleep(2000);
        clickByXpath(busLink);
    }

    public void enterCities() throws InterruptedException {
        enterValues(leavingFrom, "Hydera", "Hyderabad");
        enterValues(goingTo, "Vijaya", "Vijayawada");
    }


    public void generateDate() throws InterruptedException {
//        String[] elementValue = getElementValue(onwardJourneyDate);
        String[] date = getElementValue(datePicker);
        // Step 1: Generate random date between now and 4 months from now
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusMonths(4);
        long days = ChronoUnit.DAYS.between(start, end);
        Random rand = new Random();
        long randomDay = (long) (rand.nextDouble() * (days + 1));
        LocalDate targetDate = start.plusDays(randomDay);
        int day = targetDate.getDayOfMonth();
        // Step 2: Loop until calendar matches target month/year
        clickByXpath(onwardJourneyDate);
        Thread.sleep(10000);
        while (true) {
            if (targetDate.isAfter(start)) {
                clickByXpath(arrowIcon);
                break;
            }
        }
        date[1] += "[text()='" + day + "']";
        clickByXpath("Date!" + date[1]);
    }

    public void clickOnSearchLink() {
        clickByXpath(searchButton);
    }

    public void applyFilters() throws InterruptedException {
        Wait<WebDriver> wait = new FluentWait<>(GenericFunctions.getInstance().getDriver())
                .withTimeout(Duration.ofSeconds(30)) // Maximum wait time
                .pollingEvery(Duration.ofSeconds(5)) // Polling interval
                .ignoring(NoSuchElementException.class); // Exception to ignore during polling
        // Use Fluent Wait to wait for an element
        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.xpath(Objects.requireNonNull(getElementValue(busTypeFilters))[1])); // Replace with your locator
            }
        });
//            Ac Filter
        filtersApply(busTypeFilters, "AC");
        Thread.sleep(10000);
        filtersApply(busTypeFilters, "Sleeper");
        Thread.sleep(10000);
        filtersApply(seatFilterDeparture, "5 PM - 11 PM");
        Thread.sleep(10000);
        String status = getTextByXpath_Attribute("//*[@id=\"list-filter-container\"]/div/div[1]/div[1]", "class");
        if (!status.contains("active")) {
            clickByXpath("Filter!//*[@id=\"list-filter-container\"]/div/div[1]/div[1]");
            List<WebElement> busPartnerName = GenericFunctions.getInstance().getDriver().findElements(By.xpath(Objects.requireNonNull(getElementValue(busPartnerList))[1]));
            String name = busPartnerName.get(getRandomNumber(busPartnerName.size())).getText();
            if (name.equalsIgnoreCase("apsrtc") || name.equalsIgnoreCase("tgsrtc")) {
                clickByXpath(viewMoreBuses);
                reportStep("Element found for apstrct or tsrtc" + Objects.requireNonNull(getElementValue(viewMoreBuses))[0], "INFO");
            }
            String xpath = Objects.requireNonNull(getElementValue(busPartnerList))[1] + "[text()='" + name + "']";
            clickByXpath("busPartnerList!" + xpath);
        } else {
            List<WebElement> busPartnerName = GenericFunctions.getInstance().getDriver().findElements(By.xpath(Objects.requireNonNull(getElementValue(busPartnerList))[1]));
            String name = busPartnerName.get(getRandomNumber(busPartnerName.size())).getText();
            String xpath = Objects.requireNonNull(getElementValue(busPartnerList))[1] + "[text()='" + name + "']";
            clickByXpath("busPartnerName!" + xpath);
        }
//            price sorting
//        clickByXpath(priceSortingIcon);
    }


    public void filtersApply(String xpath, String type) {
        String[] element = getElementValue(xpath);
        List<WebElement> text = GenericFunctions.getInstance().getDriver().findElements(By.xpath(Objects.requireNonNull(element)[1]));
        element[1] += "/span[text()='" + type + "']";
        text.stream().filter(webElement -> webElement.getText().equalsIgnoreCase(type)).findFirst().ifPresent(WebElement::click);
//        for (WebElement opt : text) {
//            if (opt.getText().equalsIgnoreCase(type)) {
//                GenericFunctions.getInstance().getDriver().findElement(By.xpath(element[1])).click();
//                reportStep("User applied filters for" + " " + element[0] + " " + type, "PASS");
//            }
//        }
    }


    public void login() throws InterruptedException {
        clickByXpath(loginLink);
        enterDataByXpath(loginInput, "7995769470");
        clickByXpath(loginButton);
        Thread.sleep(20000);

    }

    public void selectSeatAndProceedForBooking() throws InterruptedException {
        Thread.sleep(10000);
        String[] errorMsg = getElementValue(errorMessage);
        List<WebElement> elements = GenericFunctions.getInstance().getDriver().findElements(By.xpath(Objects.requireNonNull(errorMsg)[1]));
        if (elements.size() == 1) {
            System.out.println("Error Message" + " " + GenericFunctions.getInstance().getDriver().findElement(By.xpath(errorMsg[1])).getText());
            Assert.fail("Couldn't complete");
        } else {
            int count = GenericFunctions.getInstance().getDriver().findElements(By.xpath(Objects.requireNonNull(getElementValue(busList))[1])).size();
            reportStep("total no.of buses and index" + " " + count + " " + getRandomNumber(count), "INFO");
            List<WebElement> webElements = GenericFunctions.getInstance().getDriver().findElements(By.xpath(Objects.requireNonNull(getElementValue(seatSelection))[1]));
            Thread.sleep(1000);
            String id = webElements.get(0).getAttribute("id");
            String xpath = "(//button[@id='" + id + "'])[1]";
            System.out.println("id before" + xpath);
            xpath = "//button[@id='" + id + "']";
            System.out.println("id after" + xpath);
            clickByXpath("seat!" + xpath);
            Thread.sleep(10000);
            int availableSeats = GenericFunctions.getInstance().getDriver().findElements(By.xpath("//*[@id=\"seat-layout-details\"]/tbody/tr/td/div/button/span")).size();
            reportStep("AVAILABLE SEATS" + " " + availableSeats, "INFO");
            int idx = getRandomNumber(availableSeats);
            Thread.sleep(30000);
            String[] seatAvailability = getElementValue(availableSeat);
            System.out.println("seat selection" + seatAvailability[1]);
            GenericFunctions.getInstance().getDriver().findElement(By.xpath(Objects.requireNonNull(seatAvailability)[1])).click();
            reportStep("seat selected with index" + " " + seatAvailability[0], "INFO");
            Thread.sleep(10000);
            String[] boardingDroppingLocations = getElementValue(boardingLocationsAndDroppingLocations);
            GenericFunctions.getInstance().getDriver().findElement(By.xpath(boardingDroppingLocations[1])).click();
            Thread.sleep(10000);
            GenericFunctions.getInstance().getDriver().findElement(By.xpath(boardingDroppingLocations[1])).click();
            GenericFunctions.getInstance().getDriver().findElement(By.xpath(Objects.requireNonNull(getElementValue(proceedButton))[1])).click();
        }

    }


    public void getFareDetails() throws InterruptedException {
//        Thread.sleep(1000);
//        new WebDriverWait(Duration.ofMinutes(1), ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login-with-google\"]/a")));
        waitForEle("//*[@id=\"login-with-google\"]/a");
        clickByXpath(skipLink);
        enterDataByXpath(passengerContact, "7995769470");
        enterDataByXpath(passengerEmail, "ravitejathota158@gmail.com");
        enterDataByXpath(passengerName, "Thota Ravi Teja");
        enterDataByXpath(passengerAge, "24");
        clickByXpath(refundSection);
        clickByXpath(fareDetailsDropdown);
        getTextByXpath(fareDetailsDropdown);
        getTextByXpath(busFare);
        getTextByXpath(basPartnerGST);
        getTextByXpath(busPartnerDiscount);
        getTextByXpath(totalFare);
    }
}
