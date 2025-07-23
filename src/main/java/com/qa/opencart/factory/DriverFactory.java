package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {
	
	WebDriver driver;
	Properties prop;
	OptionsManager optionsmanager;
	public static ThreadLocal<WebDriver> tlDriver=new ThreadLocal<WebDriver>();
	public static String highlight;
	
	
	private static final Logger log= LogManager.getLogger(DriverFactory.class);
	/**
	 * This method is used to init driver on basis of the browser name
	 * @param browserName
	 * @return
	 */
	
	public WebDriver initDriver(Properties prop) {
		
		log.info("properties:" + prop);
		
		//ChainTestListener.log("properties used:"+ prop); this logs are not printed in reports as they are in factory page
		String browserName=prop.getProperty("browser");
		System.out.println("browser name is:"+ browserName);
		//ChainTestListener.log("browser name is:" + browserName);
		
		log.info("browser name is:" +browserName);
		optionsmanager= new OptionsManager(prop);
		highlight= prop.getProperty("highlight");
		
		switch(browserName.toLowerCase().trim()) {
		case "chrome":
			tlDriver.set(new ChromeDriver(optionsmanager.getChromeOptions()));
			break;
		case "firefox":
			tlDriver.set(new FirefoxDriver(optionsmanager.getFirefoxOptions()));
			break;
		case "safari":
			tlDriver.set(new SafariDriver());
			break;
		
		case "edge":
			tlDriver.set(new EdgeDriver(optionsmanager.getEdgeOptions()));
			break;
		
		default: 
			log.error("please pass valid browser name");
			System.out.println("pls pass valid browser:"+ browserName);
		throw new BrowserException("===========INVALID BROWSER===============");
		}
		
		getDriver().get(prop.getProperty("url"));
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		return getDriver();
	}
	
	
	
	
	
	/**
	 * getDriver: get the local thread copy of the driver
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	
	/**
	 * this is used to init config properties
	 * @return
	 */
	//mvn clean install -Denv="qa"
	public Properties initProp() {
		String envName=System.getProperty("env");//system is a java get property
		FileInputStream ip=null;
		prop= new Properties();
		try {
		if(envName==null) {
			System.out.println("env is null,hence running in QA environment");
			log.warn("env is null,hence running the tests on QA env by default");
			ip= new FileInputStream("./src/test/resources/config/qa.config.properties");
		}else {
			System.out.println("Runnimg tests in evironment:" + envName);
			switch (envName.toLowerCase().trim()) {
			case "qa":
				System.out.println("running in QA environment");
				ip=new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "stage":
				System.out.println("running in Stage environment");
				ip=new FileInputStream("./src/test/resources/config/stage.config.properties");
				break;
			case "dev":
				System.out.println("running in DEV environment");
				ip=new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			case "uat":
				System.out.println("running in UAT environment");
				ip=new FileInputStream("./src/test/resources/config/uat.config.properties");
				break;
			default:
				log.error("invalid env name:" + envName);
				throw new FrameworkException("=======INAVLID ENVIRONMENT NAME:" + envName);
				
				
			}
		}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
			
		}
		
		try {
			prop.load(ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
		
	}
	
	
	
	
	/*
	 * ScreenShot
	 */
	
	
	
	
	public static File getScreenShotFile() {
	File srcFile=((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
	return srcFile;
	}
	
	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir

	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir

	}
}
