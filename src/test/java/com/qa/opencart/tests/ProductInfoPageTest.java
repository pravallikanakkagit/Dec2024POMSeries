package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.CSVUtil;
import com.qa.opencart.utils.ExcelUtil;

public class ProductInfoPageTest extends BaseTest{
	
	@BeforeClass
	public void productInfoSetup() {
		
			accPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	@DataProvider
	public Object[][] getProductTestData() {
		return new Object[][] {
			{"macbook", "MacBook Pro"},
			{"macbook", "MacBook Air"},
			{"imac","iMac"},
			{"samsung","Samasung SyncMaster 941BW"},
			{"samsung","Samsung Galaxy Tab 10.1"}
		};
	}
	
	@Test(dataProvider ="getProductTestData")
	public void productHeaderTest(String searchKey,String productName) {
		searchResultsPage=accPage.doSearch(searchKey);
		productInfoPage=searchResultsPage.selectProduct(productName);
		String actHeader=productInfoPage.getProductHeader();
		Assert.assertEquals(actHeader, productName);
	}
	
	@DataProvider
	public Object[][] getProductImagesTestData() {
		return new Object[][] {
			{"macbook", "MacBook Pro", 4},
			{"macbook", "MacBook Air", 4},
			{"imac","iMac", 3},
			{"samsung","Samasung SyncMaster 941BW", 1},
			{"samsung","Samsung Galaxy Tab 10.1", 7}
		};
	}
	
	@DataProvider
	public Object[][] getProductSheetData() {
		Object product[][]=ExcelUtil.getTestData(AppConstants.PRODUCT_SHEET_NAME);
		return product;
	}
	
	
	@DataProvider
	public Object[][] getCSVdata() {
		return CSVUtil.csvData("product");
	}
	
	
	@Test(dataProvider ="getProductSheetData")
	public void productImageCountTest(String searchKey, String productName, String expectedImageCount) {
		searchResultsPage=accPage.doSearch(searchKey);
		productInfoPage=searchResultsPage.selectProduct(productName);
		int actImageCount=productInfoPage.getProductImagesCount();
		Assert.assertEquals(String.valueOf(actImageCount), expectedImageCount);
	}
	
	@Test
	public void productInfoTest() {
		searchResultsPage=accPage.doSearch("macbook");
		productInfoPage=searchResultsPage.selectProduct("MacBook Pro");
		Map<String,String> actualProductionDetailsMap=productInfoPage.getProductDetailsMap();
		
		SoftAssert softAssert= new SoftAssert();
		softAssert.assertEquals(actualProductionDetailsMap.get("productheader"), "MacBook Pro");
		softAssert.assertEquals(actualProductionDetailsMap.get("productimages"), "4");
		
		
		softAssert.assertEquals(actualProductionDetailsMap.get("Brand"), "Apple");
		softAssert.assertEquals(actualProductionDetailsMap.get("ProductCode"), "Product 18");
		softAssert.assertEquals(actualProductionDetailsMap.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(actualProductionDetailsMap.get("productprice"), "$2,000.00");
		
		softAssert.assertAll();
	}

}
