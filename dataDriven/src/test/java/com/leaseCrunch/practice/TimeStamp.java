package com.leaseCrunch.practice;

import java.util.Date;

public class TimeStamp {

	public static void main(String[] args) {


		Date d = new Date();
		System.out.println(d);
		
		String screenshotName = d.toString().replace(":", "_").replace(" ", "_")+".jpg";
		System.out.println(screenshotName);

	}

}
