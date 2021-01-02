package com.leaseCrunch.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.leaseCrunch.base.TestBase;

public class TestUtil extends TestBase {
	
	
	public static String screenshotPath;
	public static String screenshotName;
	public static void captureScreenshot() throws IOException {
		
	File screenshotfile=	((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	Date d = new Date();
	screenshotName = d.toString().replace(":", "_").replace(" ", "_")+".jpg";
		FileUtils.copyFile(screenshotfile, new File(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+screenshotName));
		
	}
	
	@DataProvider(name="dp")	
	public Object[][] getData(Method m){
		String sheetName = m.getName();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][1];
		
		Hashtable<String,String> table = null;
		
		for (int rowNum = 2; rowNum <= rows; rowNum++) { // 2
			
			table = new Hashtable<String,String>();
			
			for (int colNum = 0; colNum < cols; colNum++) {
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data [rowNum-2][0] = table;
				// data[0][0]
				//table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				//data[rowNum - 2][0] = table;
			}

		}

		return data;
		
	
	}
	
	public static boolean isRunnable(String testname,ExcelReader excel) {
		
		String sheetname = "testsuite";
		
		int rows = excel.getRowCount(sheetname);
		
		for(int rNum =2; rNum<=rows; rNum++ ) {
			
		String testcase = excel.getCellData(sheetname, "TCID", rNum);
		
		if (testcase.equalsIgnoreCase(testname)) {
			
			String runmode = excel.getCellData(sheetname, "Runmode", rNum);
			if(runmode.equalsIgnoreCase("Y")) {
				return true;
			}else {return false;}
		}
		}
		
		return false;
		
	}
	
}
	