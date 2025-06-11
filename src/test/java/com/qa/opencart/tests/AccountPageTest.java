package com.qa.opencart.tests;

import static com.qa.opencart.constants.AppConstants.*;


import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;


public class AccountPageTest extends BaseTest {
	

	@BeforeClass
	public void accPageSetUp() {
		accPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void accPageTitleTest() {
		Assert.assertEquals(accPage.getAccPageTitle(), HOME_PAGE_TITLE);
	}
	
	@Test
	public void accPageURLTest() {
		Assert.assertTrue(accPage.getAccPageURL().contains(HOME_PAGE_FRACTION_URL));
	}

	
	
	@Test
	public void accPageHeadersTest() {
		List<String> actHeadersList=accPage.getAccPageHeaders();
		Assert.assertEquals(actHeadersList, AppConstants.expectedHeadersList);
	}
}
