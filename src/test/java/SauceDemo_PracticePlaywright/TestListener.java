package SauceDemo_PracticePlaywright;

import java.io.IOException;

import java.nio.file.Path;
import java.util.Optional;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import com.microsoft.playwright.Page;

//import ParallelRun_Demo.BaseTest;
import io.qameta.allure.Allure;
import org.opentest4j.AssertionFailedError;
import io.qameta.allure.model.StatusDetails;

public class TestListener implements BeforeTestExecutionCallback,
AfterTestExecutionCallback,
TestWatcher{

	static int total;
	static int passcount;
	static int failcount;
	
	
	@Override
	public void beforeTestExecution(ExtensionContext context) throws Exception {
		
		
		BaseTest testInstance =
	            (BaseTest) context.getRequiredTestInstance();
		
		System.out.println("===== TestListener: beforeTestExecution =====");
		String uniqueId = context.getUniqueId();
	    String className = context.getRequiredTestClass().getName();
	    String methodName = context.getRequiredTestMethod().getName();
	    System.out.println("===== Test Started =====");
	    System.out.println("Test Name   : " + context.getDisplayName());
	    System.out.println("Unique ID   : " + uniqueId);
	    System.out.println("Class       : " + className);
	    System.out.println("Method      : " + methodName);
       // System.out.println("Test Name : " + context.getDisplayName());
	    Allure.step("===== Test Started =====");
	    Allure.step("Test Name : " + context.getDisplayName());

	    Allure.parameter("JUnit Unique ID", uniqueId);
        
	}

	@Override
	public void afterTestExecution(ExtensionContext context) throws Exception {
		// TODO Auto-generated method stub
	
		total++;
		
		BaseTest testInstance =(BaseTest) context.getRequiredTestInstance();
		Page page = testInstance.page;
		System.out.println("===== TestListener: afterTestExecution=====");
        System.out.println("TestListener BaseTest.page = " + page);
		
		System.out.println("Listener BaseTest class: " + BaseTest.class.getName());		
		
		Allure.step("===== Test Finished =====");
        Allure.step("Test Name : " + context.getDisplayName());
        System.out.println("===== Page====="+ page);
        
        System.out.println("===== code to chk context.getExecutionException() in afterTestExecution =====");
        //Determine test execution status either pass or fail
        Optional<Throwable> exception = context.getExecutionException();

       // System.out.println("Execution Exception : " + exception);
        String name=context.getDisplayName().replace("()", "");
        System.out.println("Class Name: "+context.getRequiredTestClass());
        System.out.println("Method Name:"+context.getRequiredTestMethod());
        String status;
        if (exception.isPresent()) {
            status="FAIL";
        	System.out.println("Status : FAIL");
            System.out.println("Exception Type : " + exception.get().getClass().getSimpleName());
            System.out.println("Exception Message : " + exception.get());
            
            Throwable cause = exception.get();
            
            System.out.println("CAUSE CLASS = " + cause.getClass());
            System.out.println("CAUSE TYPE  = " + cause.getClass().getName());
            System.out.println("EXPECTED TYPE = "
                    + AssertionFailedError.class.getName());

            System.out.println("INSTANCEOF = "
                    + (cause instanceof AssertionFailedError));
            
            if (cause instanceof AssertionFailedError assertionError) {

            	
            	System.out.println("****** ENTERED ASSERTION BLOCK ******");
            	
                String expected = String.valueOf(
                    assertionError.getExpected().getValue()
                );

                String actual = String.valueOf(
                    assertionError.getActual().getValue()
                );
            
                Allure.getLifecycle().updateTestCase(result -> {
                    result.setStatusDetails(
                        new StatusDetails()
                            .setMessage(cause.getMessage())
                            .setExpected(expected)
                            .setActual(actual)
                    );
                    
                    System.out.println("===== AFTER TEST EXECUTION - ALLURE UPDATE =====");
                    System.out.println("Expected = "
                            + result.getStatusDetails().getExpected());
                    System.out.println("Actual = "
                            + result.getStatusDetails().getActual());
                    System.out.println("Message = "
                            + result.getStatusDetails().getMessage());
                    
                });
            }
            
               if (page != null && !page.isClosed()){
            	
            	
            	System.out.println("Status : FAIL");
            	Allure.step("Test case failed");
            	 ScreenshotUtil.captureScreenshot(page, name, status);
                 ScreenshotUtil.attachScreenshot(name, status);
               }
              else
              {
            	System.out.println("Status : FAIL");
            	Allure.step("Test case failed");
            	System.out.println("Page already closed. Screenshot cannot be captured.");
            	Allure.addAttachment(
                        "Failure Reason",
                        exception.toString()
                    );	
               }
            
        } 
        
        else 
        {
        	status="PASS";
        	System.out.println("Status : PASS");
        	//Allure.step("Test case passed");
        	 ScreenshotUtil.captureScreenshot(page, name, status);
            //ScreenshotUtil.attachScreenshot(page,name, status);
        }
        
}
     
        
        

	@Override
    public void testSuccessful(ExtensionContext context) {
		
		
		passcount++;
		BaseTest testInstance =
	            (BaseTest) context.getRequiredTestInstance();
		Page page = testInstance.page;
		//Path videoPath = page.video().path();
		String name=context.getDisplayName().replace("()", "");
        String status="PASS";
		System.out.println("----Listener: testSuccessful------");
		System.out.println("PASS : " + context.getDisplayName());
		Allure.step("Test case passed");
		
		
		ScreenshotUtil.attachScreenshot(name, status);	    
		
		
		
		VideoshotUtil.attachVideo(name, status,page);		
        
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
    	
    	failcount++;
    	BaseTest testInstance =
	            (BaseTest) context.getRequiredTestInstance();
		Page page = testInstance.page;
    	System.out.println("----testFailed------");
    	System.out.println("FAIL : " + context.getDisplayName().replace("()", ""));
    	String name=context.getDisplayName().replace("()", "");
        String status="FAIL";
        System.out.println("Reason : " + cause.getMessage());
        //ScreenshotUtil.attachVideo(name, status);
       
        /*
        if (cause instanceof AssertionFailedError assertionError) {
        String expected = String.valueOf(assertionError.getExpected().getValue());
        String actual =  String.valueOf(assertionError.getActual().getValue());
        //System.out.println(" expected with getvalue: "+String.valueOf(assertionError.getExpected().getValue()));
        //System.out.println(" actual with getvalue: "+String.valueOf(assertionError.getActual().getValue()));
        

        System.out.println("Expected : " + expected);
        System.out.println("Actual   : " + actual);
        
        Allure.getLifecycle().updateTestCase(result -> {
            result.setStatusDetails(
                new StatusDetails()
                    .setMessage(cause.getMessage())
                    .setExpected(expected)
                    .setActual(actual)
        
            		);
            System.out.println("ALLURE UPDATE:");
            System.out.println("Expected = " + result.getStatusDetails().getExpected());
            System.out.println("Actual   = " + result.getStatusDetails().getActual());
            System.out.println("Message  = " + result.getStatusDetails().getMessage());
        
        });
       
        }
        
       */
			VideoshotUtil.attachVideo(name, status, page);
    }
    
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        System.out.println("DISABLED : " + context.getDisplayName().replace("()", ""));
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        System.out.println("ABORTED : " + context.getDisplayName().replace("()", ""));
    }
	
    
    public static int getTotal() {
    	
    	System.out.println("Total cases " + total);
    	
        return total;
        
        
	
         }
    

public static int getfailcount() {
    	
    	System.out.println("Total cases " + failcount);
    	
        return failcount;
        
        
	
         }
public static int getpasscount() {
	
	System.out.println("Total cases " + passcount);
	
    return passcount;
    
    

     }
    
    }
