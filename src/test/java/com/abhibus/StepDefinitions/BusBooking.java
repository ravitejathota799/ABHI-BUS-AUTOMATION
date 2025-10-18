package com.abhibus.StepDefinitions;

import com.abhibus.Factory.WebDriverManager;
import com.abhibus.PageObjects.Locators;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import org.testng.annotations.Listeners;

public class BusBooking extends WebDriverManager{
	Locators locators = new Locators(WebDriverManager.getInstance().getDriver());

	@Given("User navigate to the abhibus website")
	public void user_navigate_to_the_abhibus_website() {
		try{
			locators = new Locators(WebDriverManager.getInstance().getDriver());
		} catch (Exception e) {
			e.fillInStackTrace();
		}
	}

	@Then("user logins into the application")
	public void login(){
		try{
			locators.login();
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.fillInStackTrace();
			Assert.fail("Couldn't login");
		}
	}
	@When("User click on buses link")
	public void user_click_on_buses_link() {
		try{
			locators.clickOnBusLink();
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.fillInStackTrace();
			Assert.fail("Couldn't click on buses");

		}
	}

	@When("User enter details like leaving city and going city date")
	public void user_enter_details_like_leaving_city_and_going_city_date() {
	    try{
			locators.enterCities();
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.fillInStackTrace();
			Assert.fail("Couldn't enter source and destination details");

		}
	}

	@Then("User click on search button")
	public void user_click_on_search_button() {
		try{
			locators.generateDate();
			locators.clickOnSearchLink();
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.fillInStackTrace();
			Assert.fail("Couldn't click on search button");

		}
	}

	@Then("User apply filters")
	public void user_apply_filters() {
	    try{
			locators.applyFilters();
			Assert.assertTrue(true);
		}catch (Exception e){
			e.fillInStackTrace();
			Assert.fail("Couldn't apply filters");
		}
	}


	@Then("User selects the bus and book")
	public void booking(){
		try{
			locators.selectSeatAndProceedForBooking();
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.fillInStackTrace();
			Assert.fail("Couldn't book seat");
		}
	}

	@Then("User gets details of the bus fare")
	public void userGetsDetailsOfTheBusFare() {
		try{
			locators.getFareDetails();
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.fillInStackTrace();
			Assert.fail("Couldn't get fare details");
		}
	}
}
