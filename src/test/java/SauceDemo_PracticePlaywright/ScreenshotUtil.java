package SauceDemo_PracticePlaywright;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.microsoft.playwright.Page;

import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


public class ScreenshotUtil {
	private static byte[] latestScreenshot;
	static Path ScreenshotPath, VideoPath;
	
	public static void captureScreenshot(Page page, String testName, String status) {

		System.out.println("--------captureScreenshot--------");
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		System.out.println("Test execution status"+status);
		
		ScreenshotPath=Paths.get("screenshots/" + testName + "_" + status + "_" + timestamp + ".png");
		/*
		latestScreenshot=page.screenshot(
                new Page.ScreenshotOptions()
                        .setPath(ScreenshotPath)
                        .setFullPage(true));
                        */
		latestScreenshot=page.screenshot(
                new Page.ScreenshotOptions()
                        .setPath(ScreenshotPath));

        System.out.println("Screenshot captured successfully");
        System.out.println("Screenshot path is:"+ScreenshotPath);
        Allure.step("===== Screenshot captured successfully =====");
	
	}
	
	public static void attachScreenshot(String testName, String status) {

		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		//byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
		//ByteArrayInputStream screenshot=new ByteArrayInputStream(latestScreenshot);
	    Allure.addAttachment("Screenshot_"+ testName +"_"+status+"_"+timestamp,"image/png", new ByteArrayInputStream(latestScreenshot),".png");
		//Allure.addAttachment("Screenshot_"+ testName +"_"+status+"_"+timestamp,"image/png", screenshot,".png");
	    System.out.println("Screenshot attached in report successfully");
        Allure.step("===== Screenshot attached in report successfully =====");
        //screenshot.close();
	}

	

}


