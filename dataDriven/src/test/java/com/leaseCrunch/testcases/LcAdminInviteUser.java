package com.leaseCrunch.testcases;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.leaseCrunch.base.TestBase;
import com.leaseCrunch.utilities.TestUtil;

public class LcAdminInviteUser extends TestBase {
	
	@Test(dataProviderClass = TestUtil.class,dataProvider ="dp")
	public static void inviteUsers(Hashtable<String,String> data) throws InterruptedException {
		
		 
		log.debug("inside invite user test.");
		click("invitebtn");
		Thread.sleep(3000);
		type("inviteuserpopup_email",data.get("email"));
		type("inviteuserpopup_firstname",data.get("firstname"));
		type("inviteuserpopup_lastname",data.get("lastname"));
		click("inviteuserpopup_invitebtn");
		Thread.sleep(3000);
		//click("invitebtn");
		//Thread.sleep(3000);
		
	}

}
