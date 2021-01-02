package com.leaseCrunch.practice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestProperties {
	
	
	public static void main(String[] args) throws IOException {
		
		System.out.println(System.getProperty("user.dir"));
		Properties config = new Properties();
		Properties or = new Properties();
		FileInputStream configfis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
		FileInputStream orfis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
		config.load(configfis);
		or.load(orfis);
		System.out.println(config.getProperty("browser"));
		System.out.println(or.getProperty("lcadminusername"));

		
		
	}
	
	//wait until the daynamic element it not get present
	
	WebDriver driver = new FirefoxDriver();
	WebElement myDynamicElement = (new WebDriverWait(driver, 60)).until(new ExpectedCondition<WebElement>(){
	             
	public WebElement apply(WebDriver d) {
	          return d.findElement(By.id("myDynamicElement"));
	     }});


}
