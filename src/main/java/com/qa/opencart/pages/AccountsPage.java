package com.qa.opencart.pages;

import static com.qa.opencart.constants.AppConstants.*;



import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.utils.ElementUtil;


public class AccountsPage {
	private WebDriver driver; // here driver is pointing to null hence we create a constructor and assign a value
	private ElementUtil eleUtil;
	
	
	private final By headers= By.cssSelector("div#content > h2");
	private final By search= By.name("search");
	private final By searchIcon= By.cssSelector("div#search button");
	private static final Logger log= LogManager.getLogger(AccountsPage.class);
	
	
	public AccountsPage(WebDriver driver) {
		this.driver= driver;
		eleUtil= new ElementUtil(driver);
	}
	
	public String getAccPageTitle() {
		String title= eleUtil.waitFotTitleIs(HOME_PAGE_TITLE, DEFAULT_TIMEOUT);
		System.out.println("home page title is:"+ title);
		return title;
	}
	
	
	public String getAccPageURL() {
		String url= eleUtil.waitForURLContains(HOME_PAGE_FRACTION_URL, DEFAULT_TIMEOUT);
		System.out.println("home page url is:"+ url);
		return url;
	}
	
	public List<String> getAccPageHeaders() {
		List<WebElement> headersList= eleUtil.getElements(headers);
		List<String>  headerValList=new ArrayList<String>();
		for(WebElement e: headersList) {
			String headerVal=e.getText();
			headerValList.add(headerVal);
			System.out.println(headerVal);
		}
		System.out.println(headerValList);
		return  headerValList;
	}
	
	
	public SearchResultsPage doSearch(String searchKey) {
		log.info("search key:" + searchKey);
		eleUtil.doSendKeys(search, searchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultsPage(driver);
	}
	
}
