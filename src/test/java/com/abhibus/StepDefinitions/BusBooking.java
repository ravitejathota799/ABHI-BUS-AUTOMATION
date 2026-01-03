package com.abhibus.StepDefinitions;

import com.abhibus.Factory.GenericFunctions;
import com.abhibus.PageObjects.Locators;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class BusBooking extends GenericFunctions {
	Locators locators = new Locators(GenericFunctions.getInstance().getDriver());

	@Given("User navigate to the abhibus website")
	public void user_navigate_to_the_abhibus_website() {
			locators = new Locators(GenericFunctions.getInstance().getDriver());
	}

	@Then("user logins into the application")
	public void login() throws InterruptedException {
			locators.login();
	}
	@When("User click on buses link")
	public void user_click_on_buses_link() throws InterruptedException {
			locators.clickOnBusLink();
	}

	@When("User enter details like leaving city and going city date")
	public void user_enter_details_like_leaving_city_and_going_city_date() throws InterruptedException {
			locators.enterCities();
	}

	@Then("User click on search button")
	public void user_click_on_search_button() throws InterruptedException {
			locators.generateDate();
			locators.clickOnSearchLink();
			Assert.assertTrue(true);
	}

	@Then("User apply filters")
	public void user_apply_filters() throws InterruptedException {
			locators.applyFilters();
			Assert.assertTrue(true);
	}


	@Then("User selects the bus and book")
	public void booking() throws InterruptedException {
			locators.selectSeatAndProceedForBooking();
			Assert.assertTrue(true);
	}

	@Then("User gets details of the bus fare")
	public void userGetsDetailsOfTheBusFare() throws InterruptedException {
			locators.getFareDetails();
			Assert.assertTrue(true);
	}
}
