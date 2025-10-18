package com.abhibus.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

	 protected static WebDriver driver;
	   public BasePage(WebDriver driver)
	     {
		     this.driver=driver;
		     PageFactory.initElements(driver,this);  //creating page factory to initialize elements
	     }

    public BasePage() {
    }
}





