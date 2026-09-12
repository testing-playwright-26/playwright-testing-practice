package PlaywrightScriptPractice;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.BoundingBox;
import com.microsoft.playwright.options.MouseButton;

import io.qameta.allure.Allure;
@ExtendWith(TestListener.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Wikipedia_Search_Interactions extends BaseTest{
	String expectedTitle = "Automation Testing Practice: PlaywrightPractice";
    String actualTitle;
    
	@Test
	//@Order(3)
    void Wikipedia_Search() {
    	
		System.out.println("---Locators Testing---");
    	
       	Allure.step("Test script flow started !!!");
    	   	
    	page.navigate("https://testautomationpractice.blogspot.com/");
    	page.waitForTimeout(1000);
    	Allure.step("Navigate to https://testautomationpractice.blogspot.com");
    	
    	
    	Locator link=page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("PlaywrightPractice"));
    	link.click();
    	System.out.println("link "+link.textContent()+" clicked");
    	Allure.step("*** Playwright option clicked****");
    	actualTitle=page.title();
    	System.out.println("Actual title after clicking playwright option:"+actualTitle);
        System.out.println("Expected title for playwright link: " + expectedTitle);

        Allure.step("Expected title after navigation: " +actualTitle);
        Allure.step("Actual title after navigation: " +expectedTitle);
        
        assertEquals(expectedTitle, actualTitle,"Page title mismatch");
        Allure.step("Verified Title match --" + expectedTitle);
        
     // Store the original page 
        Page originalPage = page;
        
        page.locator("input.wikipedia-search-input").fill("DevOps");
        page.waitForTimeout(3000);
        page.locator("input.wikipedia-search-button").click();
        page.waitForTimeout(3000);
        Locator resultsContainer=page.locator("div#Wikipedia1_wikipedia-search-results");
       
        resultsContainer.waitFor();

        System.out.println("Container visible: " + resultsContainer.isVisible()
        );
        Locator searchResults = page.locator( "div#Wikipedia1_wikipedia-search-results a" );
        searchResults.first().waitFor();
        
        int resultCount = searchResults.count(); 
        System.out.println(" Searchresults count: "+resultCount);
        for (int i = 0; i < resultCount; i++) 
         { 
        	System.out.println( "Inner text: "+searchResults.nth(i).innerText() );
         }
        
        
        String expectedValue = "DevOps";
        
        Assertions.assertTrue( searchResults.allInnerTexts().contains(expectedValue), "Expected search result is not displayed" );
        
        Locator expectedResult = resultsContainer.getByRole(
                AriaRole.LINK,
                new Locator.GetByRoleOptions()
                        .setName("DevOps")
                        .setExact(true)
        );
        
        Page newTab = page.waitForPopup(() -> {expectedResult.click();  });
        newTab.waitForLoadState();
        String newTabTitle = newTab.title();
        
        System.out.println("New Tab Title: " + newTabTitle);
        
        
        newTab.close();
        originalPage.bringToFront();
	}
	
	
	
	
	
	
}
