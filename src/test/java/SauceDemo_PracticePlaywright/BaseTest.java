package SauceDemo_PracticePlaywright;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.qameta.allure.Allure;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
	
	
	protected Playwright playwright;
	protected Browser browser;
	public Page page;
	protected BrowserContext context;
	
	public Page getPage() {
	    return page;
	}
    
    @BeforeAll
    void launchBrowser() {
    	System.out.println("----****************------- ");
    	System.out.println("----****************------- ");
    	System.out.println("---BeforeAll---");
    	playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        
       
    }

    @AfterAll
    void closeBrowser() {
    	System.out.println("----AfterAll---");
    	
    	// 1. Retrieve test execution results
        

        // 2. Calculate QA metrics
    	
    	System.out.println("Testing Status: ");
    	System.out.println("Total no of scripts: " + TestListener.getTotal());
    	System.out.println("Total Passed: " + TestListener.passcount);
    	System.out.println("Total Failed : " + TestListener.failcount);
    	
    	
    	
    	if (browser != null) 
    	{
            browser.close();
        }
    	
    	if (playwright != null) 
    	{
            playwright.close();
        }
    	//playwright.close();
    	
    	
    	System.out.println("----****************------- ");
    	System.out.println("----****************------- ");
    }
    
    
    @BeforeEach
    void createContextAndPage() {
    
    	System.out.println("---BeforeEach----");
    	Allure.step("Browser Launch successfully");  
    	context = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get("videos/")));
    	Allure.step("Create browser context");   
        page = context.newPage();
        Allure.step("Create New Page object");
        page.setDefaultNavigationTimeout(60000);
        
        System.out.println("BaseTest.page = " + page);
        System.out.println("page          = " + page);
        System.out.println("BaseTest class = " + BaseTest.class.getName());
        
        
    }
    
    @AfterEach
    void closeContext() {
    	System.out.println("----AfterEach----- ");
    	
    	if (page != null) {
    		Allure.step("Page object closed");
    		page.close();
        }
        if (context != null) {
        	Allure.step("Context browser object closed");
        	context.clearCookies();
        	context.close(); // Flushes trace files and reports cleanly
        }
    }
    

}
