package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

import static com.qa.opencart.constants.AppConstants.*;

public class LoginPage {
	
private WebDriver driver; // here driver is pointing to null hence we create a constructor and assign a value
private ElementUtil eleUtil;
	
	
	//1.private By locators
	
	private final By email= By.id("input-email");
	private final By password=By.id("input-password");
	private final By forgotPassword=By.xpath("(//a[text()='Forgotten Password'])[1]");
	private final By login=By.xpath("//input[@value='Login']");
	private final By registerLink=By.linkText("Register");
	
	
	
	//2.public page constructor
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		eleUtil= new ElementUtil(driver);
	}
	
	
	
	//3.public page actions/methods
	@Step("getting login page title")
	public String getLoginPageTitle() {
		String title= eleUtil.waitFotTitleIs(LOGIN_PAGE_TITLE, DEFAULT_TIMEOUT);
		System.out.println("title is:" + title);
		return title;
	}
	
	@Step("getting login page URL")
	public String getLoginPageURL() {
		String url =eleUtil.waitForURLContains(LOGIN_PAGE_FRACTION_URL, DEFAULT_TIMEOUT);
		System.out.println("url is:" + url);
		return url;
	}
	
	
	@Step("checking if forgot password link exists")
	public boolean isForgotPwdLinkExists() {
		return eleUtil.isElementDisplayed(forgotPassword);
	}
	
	@Step("login with valid username: {0} and password {1}")//this means we get paramaters printed in this step {0}-username and {1}-password
	public AccountsPage doLogin(String userName,String pwd) {
		System.out.println("Credentials are:"+ userName + pwd);
		
		eleUtil.waitForElementVisible(email, MEDIUM_DEFAULT_TIMEOUT).sendKeys(userName);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(login);
		
		
		return new AccountsPage(driver);
	}
	
	@Step("navigating to the user registeration page")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.clickWhenReady(registerLink, DEFAULT_TIMEOUT);
		return new RegisterPage(driver);
	}
}
