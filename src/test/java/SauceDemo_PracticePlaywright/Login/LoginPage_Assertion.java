package SauceDemo_PracticePlaywright.Login;
import org.junit.jupiter.api.MethodOrderer;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.microsoft.playwright.Locator;

import SauceDemo_PracticePlaywright.BaseTest;
import io.qameta.allure.Allure;
import SauceDemo_PracticePlaywright.TestListener;
@ExtendWith(TestListener.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class LoginPage_Assertion extends BaseTest {
	
	//@Order(1)
	@ParameterizedTest
	@CsvSource({
	    "standard_user, secret_sauce, Swag Labs,true",
	    "locked_out_user, secret_sauce, Swag Labs,false",
	    "problem_user, secret_sauce, Swag Labs,false"
	   
	    
	})
	void TC_Login_001_SuccessfulLogin(String username, String password, String expectedtitle, boolean value)
	{
		Allure.step("Test script flow started !!!");
		Allure.step("TEST DATA: "+ username + ","+" password");
		
		Allure.step("****DESCRIPTION: Test script is to ensure that login credentials are working as expected****");
		page.navigate("https://www.saucedemo.com/");
		page.waitForTimeout(4000);
		System.out.println("LoginTest_SauceDemp BaseTest.page = " + page);
		
		
	     Allure.step("Navigate to https://www.saucedemo.com/");
	System.out.println("Locator user value before : "+page.locator("#user-name").inputValue());
	page.locator("#user-name").fill(username);
	 page.waitForTimeout(1000); 
	System.out.println("Locator user value after: "+page.locator("#user-name").inputValue());
	Allure.step("User Name: "+ username);
	
	System.out.println("Locator password value before: "+page.locator("#password").inputValue());
	page.locator("#password").fill(password);
	 page.waitForTimeout(1000);
	System.out.println("Locator passqword value after: "+page.locator("#password").inputValue());
	Allure.step("Password: "+password);	
	
	Locator Login_but=page.locator("#login-button");

	//page.wait() is used instead of page.waitforTimeout() will throw current thread is not owner error
    //submit_but.scrollIntoViewIfNeeded();
	Login_but.evaluate("el => el.scrollIntoView({ behavior: 'smooth', block: 'center' })");
    //page.waitForTimeout(2000);
	Login_but.waitFor();
	Login_but.highlight();
	Login_but.click();
	 page.waitForTimeout(1000);
	 
	 String actualtitle=page.title();
	 
	
	 //assertTrue(page.url().contains("inventory")); //login success check page url
     //Allure.step("Successful login");
    
	 if (value)
	 {
		 	 
			 assertEquals(expectedtitle,actualtitle, "Valid user credentials not working");
			 Allure.step("Successful login");
			 
			
	   } 
	 else {
		 
		 
		  if ("locked_out_user".equals(username)) {

		        String errormsg =
		            page.locator("//h3[@data-test='error']").textContent();

		        assertTrue(
		            errormsg.contains(
		                "Epic sadface: Sorry, this user has been locked out."
		            ),
		            "Locked user validation is not working"  );
		        
		        
		       }
		  else {
		 actualtitle="Intentionally changed the title";  
		 
	   	    assertEquals(expectedtitle,actualtitle, "***Intentional failure for this credentials");
		 //Allure.step("Successful login");
		 
		    }
	   
		 
	 } 
		 
		 
		 
		 
		 
		 
		 
	 
	
	
	}//method ending
	
}//class ending
    /*
	
	@Order(1)
	@Test
    void TC_Login_002_UsernameFieldValidation()
	{
	
    	page.navigate("https://www.saucedemo.com/");
    	Allure.step("Navigate to https://www.saucedemo.com/");
    	Locator username=page.getByPlaceholder("Username");
    	username.fill("");
       	Allure.step("Username is empty");
       	username.press("Enter");
    	String errormsg=page.locator("//h3[@data-test='error']").textContent();
    	
    	assertTrue(errormsg.contains("Epic sadface: Username is required"),"***Username field Validation is not working***");
    	
    	Allure.step("Username validation is displayed and login does not proceed");
		
}

	@Order(2)
	@Test
    void TC_Login_003_PasswordFieldValidation()
	{
	
    	page.navigate("https://www.saucedemo.com/");
    	Allure.step("Navigate to https://www.saucedemo.com/");
    	
    	page.getByPlaceholder("Username").fill("standard_user");
    	Locator password=page.getByPlaceholder("Password");
    	password.fill("");
       	Allure.step("Password is empty");
       	password.press("Enter");
       	
    	String errormsg=page.locator("//h3[@data-test='error']").textContent();
    	
    	assertTrue(errormsg.contains("Epic sadface: Password is required"),"***Username field Validation is not working***");
    	
    	Allure.step("Password validation is displayed and login does not proceed");
		
}

	@Order(3)
	@Test
	void TC_Login_004_InvalidUsername()
	{
	
    	page.navigate("https://www.saucedemo.com/");
    	Allure.step("Navigate to https://www.saucedemo.com/");
    	
    	page.getByPlaceholder("Username").fill("testingpro");
    	
    	page.getByPlaceholder("Password").fill("secret_sauce");
       	
    	Locator Login_but=page.locator("#login-button");
    	
    	Login_but.click();
    	String errormsg=page.locator("//h3[@data-test='error']").textContent();
    	
    	assertTrue(errormsg.contains("Epic sadface: Username and password do not match any user in this service"),"***Authentication is not failing with proper validation***");
    	
    	Allure.step("Login is not successful and Authentication validation failure message is displayed as appropriate");
		
}
	@Order(4)
	@Test
	void TC_Login_005_InvalidPassword()
	{
	
    	page.navigate("https://www.saucedemo.com/");
    	Allure.step("Navigate to https://www.saucedemo.com/");
    	
    	page.getByPlaceholder("Username").fill("standard_user");
    	
    	page.getByPlaceholder("Password").fill("testingpro");
       	
         Locator Login_but=page.locator("#login-button");
    	
    	Login_but.click();
       	
       	
    	String errormsg=page.locator("//h3[@data-test='error']").textContent();
    	
    	assertTrue(errormsg.contains("Epic sadface: Username and password do not match any user in this service"),"***Authentication is not failing with proper validation***");
    	
    	Allure.step("Login is not successful and Authentication validation failure message is displayed as appropriate");
		
}
	
	@Order(5)
	@Test
	void TC_Login_006_InvalidUsernamePassword()
	{
	
    	page.navigate("https://www.saucedemo.com/");
    	Allure.step("Navigate to https://www.saucedemo.com/");
    	
    	page.getByPlaceholder("Username").fill("invalidusernamepass");
    	
    	page.getByPlaceholder("Password").fill("testing123");
       	
         Locator Login_but=page.locator("#login-button");
    	
    	Login_but.click();
       	
       	
    	String errormsg=page.locator("//h3[@data-test='error']").textContent();
    	
    	assertTrue(errormsg.contains("Epic sadface: Username and password do not match any user in this service"),"***Authentication is not failing with proper validation***");
    	
    	Allure.step("Login is not successful and Authentication failure message is displayed as appropriate");
		
}
	
	@Order(6)
	@Test
	void TC_Login_007_EmptyUsernamePassword()
	{
	
    	page.navigate("https://www.saucedemo.com/");
    	Allure.step("Navigate to https://www.saucedemo.com/");
    	
    	page.getByPlaceholder("Username").fill("");
    	
    	page.getByPlaceholder("Password").fill("");
       	
         Locator Login_but=page.locator("#login-button");
    	
    	Login_but.click();
       	
       	
    	String errormsg=page.locator("//h3[@data-test='error']").textContent();
    	
    	assertTrue(errormsg.contains("Epic sadface: Username is required"),"***Authentication is not failing with proper validation***");
    	
    	Allure.step("Login is not successful and Authentication failure message is displayed as appropriate");
		
}

*/

	
	
	
	
