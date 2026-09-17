package SauceDemo_PracticePlaywright;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.microsoft.playwright.Page;

import io.qameta.allure.Allure;


public class VideoshotUtil {
	static Path VideoPath;


	public static void attachVideo(String testName, String status, Page page) {

		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		//byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
		
		
		VideoPath = page.video().path();
		System.out.println("video path: "+VideoPath);
		try {
		InputStream stream = Files.newInputStream(VideoPath);
		
		Allure.addAttachment(
		        "Execution Video_"+ testName +"_"+status+"_"+timestamp,
		        "video/webm",
		        stream,
		        ".webm");
		
	    stream.close();
		}
		catch (IOException e) {
		    e.printStackTrace();
		}
	    System.out.println("video attach is successfull");
        Allure.step("===== video attach is successfull=====");
	}
}
