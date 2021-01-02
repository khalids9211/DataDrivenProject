package com.leaseCrunch.testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.leaseCrunch.base.TestBase;
import com.leaseCrunch.utilities.TestUtil;


public class LoginTest extends TestBase {
	
	@Test(dataProviderClass = TestUtil.class,dataProvider ="dp")
	public void loginLcAdmin(Hashtable<String,String>data) throws IOException {
		
		/*
		 * try { softassertion("xyz", "abc"); System.out.println("After Asseration");
		 * }catch(Throwable t){
		 * 
		 * System.out.println("Inside Catch block");
		 * 
		 * }
		 */
		
		log.debug("Inside login Test.");
		type("lcadminusername",data.get("Username"));
		
		type("lcadminpassword",data.get("Password"));
		//driver.findElement(By.xpath(or.getProperty("loginbutton"))).click();
		click("loginbutton");
		Assert.assertTrue(isElementPresent(By.xpath(or.getProperty("lcadmintab"))), "Loging is not successful.");
		
		log.debug("loging test executed.");
		
		
	}
	
	

}
