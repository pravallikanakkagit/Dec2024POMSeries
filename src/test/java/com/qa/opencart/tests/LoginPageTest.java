package com.qa.opencart.tests;

import static com.qa.opencart.constants.AppConstants.HOME_PAGE_TITLE;
import static com.qa.opencart.constants.AppConstants.LOGIN_PAGE_FRACTION_URL;
import static com.qa.opencart.constants.AppConstants.LOGIN_PAGE_TITLE;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Feature("F 50: Opencart --Login feature")
@Epic("epic 100:design pages for open cart application")
@Story("US 101: Implement login page for open cart application")



public class LoginPageTest extends BaseTest {
	
	@Description("checking open cart page title")
	@Severity(SeverityLevel.MINOR)
	@Owner("Pravallika Nakka")
	@Test(description="Checking login page title test")
	public void loginPageTitleTest() {
	String actTitle=loginPage.getLoginPageTitle();
	ChainTestListener.log("checking login page title:" +actTitle);
	Assert.assertEquals(actTitle, LOGIN_PAGE_TITLE);
	}
	
	
	@Description("checking open cart page url")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Pravallika Nakka")
	@Test
	public void loginPageURLTest() {
		String actURL=loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(LOGIN_PAGE_FRACTION_URL));
	}
	
	
	@Description("checking open cart page has forgot password link")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Pravallika Nakka")
	@Test
	public void forgotPwd() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExists());
	}
	
	
	@Description("check user is able to login with valid user credentials....")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Pravallika Nakka")
	@Test(priority= Short.MAX_VALUE)
	public void doLoginTest() {
		accPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccPageTitle(), HOME_PAGE_TITLE);
	}
	
	
	
	
	
	
	
}
