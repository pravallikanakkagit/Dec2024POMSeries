package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {
	
	@BeforeClass
	public void registerSetup() {
		registerPage =loginPage.navigateToRegisterPage();
	}
	
	@DataProvider
	public Object[][] getUserRegTestData() {
		
		return new Object[][] {
			{"Monish", "Karthikeya", "8787878787","monishk@123","yes"},
			{"Pretam", "Kalyan", "7878787878","pretamk@123","no"},
			{"Anisha", "Varma", "6767676767","anishakv@123","yes"}	
		};
		
	}
	
	@DataProvider
	public Object[][] getUserRegData() {
		Object regData[][]=ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return regData;
		}
	
	
	@Test(dataProvider="getUserRegData")
	public void userRegisterTest(String firstName, String lastName, String telephone, String password, String subscribe) {
		Assert.assertTrue(registerPage.userRegisteration(firstName,firstName,telephone,password,subscribe));
	}
	
	
}
