package com.qa.opencart.base;

import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultsPage;
import com.qa.opencart.utils.LogUtil;

//@Listeners(ChainTestListener.class)
public class BaseTest {
	
	WebDriver driver;
	
	DriverFactory df;
	protected Properties prop;
	protected LoginPage loginPage;//for inheriting loginPageTestMethods we used protected as its from different package
	protected AccountsPage accPage;
	protected SearchResultsPage searchResultsPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	private static final Logger log= LogManager.getLogger(BaseTest.class);
	@Parameters({"browser"})
	@BeforeTest
	public void setup(String browserName) {
		
		df= new DriverFactory();
		prop=df.initProp();
		//browser name is passed from xml file
		if(browserName!=null) {
			prop.setProperty("browser", browserName);//here it updates the default browser value in config file to xml file browservalue based on test and parameters given
		}
		driver= df.initDriver(prop);
		loginPage = new LoginPage(driver);
		
	}
	
	
	
	@AfterMethod //will be running after each @test method
	public void attachScreenshot(ITestResult result) {
		
		 if(!result.isSuccess()) {//for failure testcases
			 log.info("screenshotis taken");
		  ChainTestListener.embed(DriverFactory.getScreenShotFile(), "image/png"); 
		 }
		//ChainTestListener.embed(DriverFactory.getScreenShotFile(), "image/png"); //for all tests even if its passed or failed
		 
	}
	
	
	@AfterTest
	public void tearDown() {
		
		driver.quit();
		log.info("closing the browser");
	}

}
