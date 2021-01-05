package com.leaseCrunch.listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.leaseCrunch.base.TestBase;
import com.leaseCrunch.utilities.MonitoringMail;
import com.leaseCrunch.utilities.TestConfig;
import com.leaseCrunch.utilities.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

public class CustomListeners extends TestBase implements ITestListener,ISuiteListener {
	
	String messageBody;
     
	public void onStart(ISuite suite) {
		MonitoringMail mail = new MonitoringMail();
		try {
			System.out.println(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			messageBody = "http://"+InetAddress.getLocalHost().getHostAddress()+":8080/job/DataDrivenLiveProject/Extent_20HTML_20Report/";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	System.out.println(messageBody);
	
		try {
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onFinish(ISuite suite) {
		
	}

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
