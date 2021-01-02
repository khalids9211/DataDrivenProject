package com.leaseCrunch.listeners;

import java.io.IOException;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.leaseCrunch.base.TestBase;
import com.leaseCrunch.utilities.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

public class CustomListeners extends TestBase implements ITestListener  {
	
     
	public void onTestSkipped(ITestResult arg0) {
		
		test.log(LogStatus.SKIP, arg0.getTestName().toUpperCase()+"Skipping the test case as runmode is set to No");
		report.endTest(test);
		report.flush();
	}
	
	public void onTestStart(ITestResult arg0) {
		
		
		test = report.startTest(arg0.getName().toUpperCase());
		// we will check whether the run mode is Y or N
		
		
		 
		 
		
	}
	
	
	
	public void onTestSuccess(ITestResult arg0) {
		
	test.log(LogStatus.PASS, arg0.getName().toUpperCase()+"PASS");
	report.endTest(test);
	report.flush();
		
	}
	
	
	
	public void onTestFailure(ITestResult arg0) {
		
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.FAIL, arg0.getName().toUpperCase()+"FAIL with exception : "+arg0.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		Reporter.log("Click to See Screen Shot");
		Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+">ScreenShot</a>");
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" heigt=200 Width=200></img></a>");
		report.endTest(test);
		report.flush();
		
	}
	
	

}
