package com.leaseCrunch.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.leaseCrunch.utilities.ExcelReader;
import com.leaseCrunch.utilities.ExtentManager;
import com.leaseCrunch.utilities.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {

	/*
	 * in Data driven pattern we intialize everthing in base class like
	 * 
	 * WebDriver
	 * Properties 
	 * logs - Log4j jar -  .log file like (Application.log,Selenium.log) - log4j.properties (appender) - logger class
	 * excel reader 
	 * mail
	 * Report ng we need two dependancy (reportng , googlec Guice)
	 * Extent report
	 * DB
	 * Click method
	 * Type method
	 */
	
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties or = new Properties();
	public static FileInputStream fis;
	public static Logger log=Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\TestData.xlsx");
	public ExtentReports report = ExtentManager.getInstance();	
	public static ExtentTest test;
	public static String browser;
	
	
	@BeforeSuite
	public void setup()  {
		
		if(driver== null) {
			
			try {
				 fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				config.load(fis);
				log.debug("config file is loaded.");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
			fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				or.load(fis);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// set browser for jenkin by using getenv 
			if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty()) {
				
				browser = System.getenv("browser");
				
			}else {
				
				browser = config.getProperty("browser");
				
			}
			//if browser is not select from the jenking choice than run the browse which is set the in config property file
			config.setProperty("browser", browser);
			
			
			if(config.getProperty("browser").equals("firefox")) {
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\geckodriver.exe");
				driver = new FirefoxDriver();
				log.debug("Firefox is launched.");
			}else if (config.getProperty("browser").equals("edge")) {
				
				System.out.println("Micrososft edge is lauanched");
				System.setProperty("webdriver.edge.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\msedgedriver.exe");
				driver = new EdgeDriver();
				log.debug("Edge is launched.");
			}else if(config.getProperty("browser").equals("chrome")) {
				
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver.exe");
				driver = new ChromeDriver();
				log.debug("Chrome is launched.");
			}
			
			
			driver.get(config.getProperty("testsiteurl"));
			log.debug("Navigated to :" +config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			//driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicitwaittime")), TimeUnit.SECONDS);
			
		}
		
		
	}
	
	public static void click(String locator) {
		
		driver.findElement(By.xpath(or.getProperty(locator))).click();
		test.log(LogStatus.INFO, "Clicking on "+locator);
	}
	
	public static void type(String locator,String value) {
		
		driver.findElement(By.xpath(or.getProperty(locator))).sendKeys(value);
		test.log(LogStatus.INFO, "Typing in"+locator + "entered value as " + value);
		
	}
	
	public static void softassertion(String expected,String actual) throws IOException {
		
		try {
			
			Assert.assertEquals(actual, expected);
			
		}catch(Throwable t) {
			
			TestUtil.captureScreenshot();
			//Report NG
			Reporter.log("<br>"+"Softasseration Failure :" + t.getMessage()+"<br>");
			Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" heigt=200 Width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			
			//Extent report
			test.log(LogStatus.FAIL, "SoftAsseration Failure : "+t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
			
		}
		
		
	}
	
	public boolean isElementPresent(By by) {
		
		try {
			driver.findElement(by);
			return true;
			
			
		}catch(NoSuchElementException e) {
			
			return false;
		}
		
	}

	@AfterSuite
	public void teardown() {
		
		driver.quit();
		log.debug("Test execution has been completed.");
	}
	
	
	
}
