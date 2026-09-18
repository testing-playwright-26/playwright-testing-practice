package SauceDemo_PracticePlaywright.SearchProduct;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.microsoft.playwright.Locator;

import SauceDemo_PracticePlaywright.BaseTest;
import io.qameta.allure.Allure;
import SauceDemo_PracticePlaywright.TestListener;
@ExtendWith(TestListener.class)
public class ProductSortingTest extends BaseTest {

   
	@Test
    @Order(1)
    void TC_001_Name_AZ() {

        Allure.step("Open SauceDemo URL");
        page.navigate("https://www.saucedemo.com/");

        Allure.step("Login with valid credentials");
        page.getByPlaceholder("Username").fill("standard_user");
        page.getByPlaceholder("Password").fill("secret_sauce");
        page.getByRole(
                com.microsoft.playwright.options.AriaRole.BUTTON,
                new com.microsoft.playwright.Page.GetByRoleOptions().setName("Login")
        ).click();
        
        page.waitForTimeout(1000);

        Allure.step("Select Name A-Z sorting");
        page.locator("[data-test='product-sort-container']")
                .selectOption("az");
        page.waitForTimeout(1000);
        Allure.step("Display total product count and names");

        Locator productNames = page.locator(".inventory_item_name");
        
        productNames.first().waitFor();
        int productCount = productNames.count();

        System.out.println("Total Products: " + productCount); 

        List<String> actualNames = productNames.allTextContents();

        for (String name : actualNames) {
            System.out.println("Product: " + name);
        }

        Allure.step("Assert products are in alphabetical A-Z order");

        List<String> expectedNames = new ArrayList<>(actualNames);
        Collections.sort(expectedNames); //sorting the list in natural alphabetical order
        
        System.out.println("ExpectedNames after sorting the reading the actual names");
        for (String name : expectedNames) {
            System.out.println("Product: " + name);
        }
        assertEquals(
                expectedNames,
                actualNames,
                "Products are not sorted in A-Z order"
        );
        
        page.waitForTimeout(1000);
    }

    
    

    @Test
    @Order(2)
    void TC_002_Name_ZA() {

        Allure.step("Open SauceDemo URL");
        page.navigate("https://www.saucedemo.com/");

        Allure.step("Login with valid credentials");
        page.getByPlaceholder("Username").fill("standard_user");
        page.getByPlaceholder("Password").fill("secret_sauce");
        page.getByRole(
                com.microsoft.playwright.options.AriaRole.BUTTON,
                new com.microsoft.playwright.Page.GetByRoleOptions().setName("Login")
        ).click();
        page.waitForTimeout(1000);

        Allure.step("Select Name Z-A sorting");
        page.locator("[data-test='product-sort-container']")
                .selectOption("za");

        Allure.step("Display total product count and names");
        page.waitForTimeout(1000);

        Locator productNames = page.locator(".inventory_item_name");

        productNames.first().waitFor();
        int productCount = productNames.count();

        System.out.println("Total Products: " + productCount);

        List<String> actualNames = productNames.allTextContents();

        for (String name : actualNames) {
            System.out.println("Product: " + name);
        }

        Allure.step("Assert products are in alphabetical Z-A order");

        List<String> expectedNames = new ArrayList<>(actualNames);
        expectedNames.sort(Collections.reverseOrder());
        
        System.out.println("ExpectedNames after sorting the reading the actual names");
        for (String name : expectedNames) {
            System.out.println("Product: " + name);
        }
        assertEquals(
                expectedNames,
                actualNames,
                "Products are not sorted in Z-A order"
     
        		);
        page.waitForTimeout(1000);
    }


   
    @Test
    @Order(3)
    void TC_003_Price_Low_High() {

        Allure.step("Open SauceDemo URL");
        page.navigate("https://www.saucedemo.com/");

        Allure.step("Login with valid credentials");
        page.getByPlaceholder("Username").fill("standard_user");
        page.getByPlaceholder("Password").fill("secret_sauce");
        page.getByRole(
                com.microsoft.playwright.options.AriaRole.BUTTON,
                new com.microsoft.playwright.Page.GetByRoleOptions().setName("Login")
        ).click();
        page.waitForTimeout(1000);

        Allure.step("Select Price Low-High sorting");
        page.locator("[data-test='product-sort-container']")
                .selectOption("lohi");

        page.waitForTimeout(1000);
        Allure.step("Display total product count and prices");

        Locator prices = page.locator(".inventory_item_price");
        prices.first().waitFor();
        
        int productCount = prices.count();

        System.out.println("Total Products: " + productCount);

        List<Double> actualPrices = getPrices(prices);

        for (Double price : actualPrices) {
            System.out.println("Actual Price: $" + price);
        	
        }

        Allure.step("Assert products are sorted by price Low-High");

        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);
        
        for (Double price : expectedPrices) {
            System.out.println("Expected Price: $" + price);
        }
        assertEquals(
                expectedPrices,
                actualPrices,
                "Products are not sorted with Price Low-High"
        );
        page.waitForTimeout(1000);
    }

    

    @Test
    @Order(4)
    void TC_004_Price_High_Low() {

        Allure.step("Open SauceDemo URL");
        page.navigate("https://www.saucedemo.com/");

        Allure.step("Login with valid credentials");
        page.getByPlaceholder("Username").fill("standard_user");
        page.getByPlaceholder("Password").fill("secret_sauce");
        page.getByRole(
                com.microsoft.playwright.options.AriaRole.BUTTON,
                new com.microsoft.playwright.Page.GetByRoleOptions().setName("Login")
        ).click();
        page.waitForTimeout(1000);
        Allure.step("Select Price High-Low sorting");
        page.locator("[data-test='product-sort-container']")
                .selectOption("hilo");
        
        page.waitForTimeout(1000);

        Allure.step("Display total product count and prices");

        Locator prices = page.locator(".inventory_item_price");
        
        prices.first().waitFor();
        int productCount = prices.count();

        System.out.println("Total Products: " + productCount);

        List<Double> actualPrices = getPrices(prices);

        for (Double price : actualPrices) {
            System.out.println("Actual Price: $" + price);
        }

        Allure.step("Assert products are sorted by price High-Low");

        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        expectedPrices.sort(Collections.reverseOrder());
        for (Double price : expectedPrices) {
            System.out.println("Expected Price: $" + price);
        }
        
        assertEquals(
                expectedPrices,
                actualPrices,
                "Products are not sorted Price High-Low"
        );
    }

    
    
    @ParameterizedTest
    @CsvSource({
        "lohi, LOW_HIGH",
        "hilo, HIGH_LOW"
    })
    void TC_005_Price_Sorting(String sortOption, String sortType) {

        Allure.step("Open SauceDemo URL");
        page.navigate("https://www.saucedemo.com/");

        Allure.step("Login with valid credentials");
        page.getByPlaceholder("Username").fill("standard_user");
        page.getByPlaceholder("Password").fill("secret_sauce");
        page.getByRole(
                com.microsoft.playwright.options.AriaRole.BUTTON,
                new com.microsoft.playwright.Page.GetByRoleOptions().setName("Login")
        ).click();
        page.waitForTimeout(1000);
        
        page.locator("[data-test='product-sort-container']")
                .selectOption(sortOption);
        
        page.waitForTimeout(1000);
        
        Allure.step("Display total product count and prices");

        Locator prices = page.locator(".inventory_item_price");
        
        prices.first().waitFor();
        int productCount = prices.count();

        System.out.println("Total Products: " + productCount);
        List<Double> expectedLowHigh = Arrays.asList(
        	    7.99, 9.99, 15.99, 15.99, 29.99, 49.99
        	);

       List<Double> expectedHighLow = Arrays.asList(
        	    49.99, 29.99, 15.99, 15.99, 9.99, 7.99
        	);
        
        
        List<Double> actualPrices = getPrices(prices);

        

        
      
        //List<Double> expectedPrices = new ArrayList<>(actualPrices);
        
        
        if(sortType.equals("HIGH_LOW"))
        {
        	
        	for (Double price : expectedHighLow) {
                System.out.println("Expected Price: $" + price);
            }
        	System.out.println("Sorting type High to Low");
        	expectedHighLow.sort(Collections.reverseOrder());
             
             
             Allure.step("Assert products are sorted by price High-Low");
             for (Double price : actualPrices) {
                 System.out.println("Actual Price: $" + price);
             }
        assertEquals(
        		expectedHighLow,
                actualPrices,
                "Products are not sorted Price High-Low"
        );
        }
        else
        {
        	for (Double price : expectedLowHigh) {
                System.out.println("Expected Price: $" + price);
            }
        	System.out.println("Sorting type Low to High");
        	Collections.sort(expectedLowHigh);
        	
        	Allure.step("Assert products are sorted by price Low-High");
        	for (Double price : actualPrices) {
                System.out.println("Actual Price: $" + price);
            }
        	assertEquals(
        			expectedLowHigh,
                    actualPrices,
                    "Products are not sorted with Price Low-High"
            );
        	
        }
        
        
        
        
        
    }


    
    private List<Double> getPrices(Locator priceLocator) {

        List<String> priceTexts = priceLocator.allTextContents();

        List<Double> prices = new ArrayList<>();

        for (String price : priceTexts) {

            //System.out.println("Price before trim: "+price);
        	String cleanPrice = price.replace("$", "").trim();

            prices.add(Double.parseDouble(cleanPrice));
        }

        return prices;
    }


}

    