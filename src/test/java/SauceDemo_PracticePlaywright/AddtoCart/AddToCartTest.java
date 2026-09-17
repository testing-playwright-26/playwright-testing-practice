package SauceDemo_PracticePlaywright.AddtoCart;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

import SauceDemo_PracticePlaywright.BaseTest;
import io.qameta.allure.Allure;
import SauceDemo_PracticePlaywright.TestListener;
@ExtendWith(TestListener.class)
public class AddToCartTest extends BaseTest {


	 private void login() {

	        page.getByPlaceholder("Username")
	                .fill("standard_user");

	        page.getByPlaceholder("Password")
	                .fill("secret_sauce");

	        page.getByRole(
	                com.microsoft.playwright.options.AriaRole.BUTTON,
	                new com.microsoft.playwright.Page.GetByRoleOptions()
	                        .setName("Login")
	        ).click();
	    
	    
	    }
	 
	
    @Test
    @Order(1)
    void TC_001_Product_count_display_on_CartLogo() {

        Allure.step("Open SauceDemo URL");
        page.navigate("https://www.saucedemo.com/");

        Allure.step("Login with valid credentials");
        login();

        Allure.step("Display total product count with names");

        Locator products = page.locator(".inventory_item");

        products.first().waitFor();
        int productCount = products.count();

        System.out.println("Total Products: " + productCount);

        Locator productNames =
                page.locator(".inventory_item_name");

        for (String name : productNames.allTextContents()) {
            System.out.println("Product: " + name);
        }

        Allure.step("Click Sauce Labs Backpack Add to Cart");

        page.locator(
                "[data-test='add-to-cart-sauce-labs-backpack']"
        ).click();

        System.out.println("Click Sauce Labs Backpack Add to Cart");
        Allure.step("Assert product count is displayed on cart logo");

        Locator cartBadge =
                page.locator(".shopping_cart_badge");

        assertTrue(
                cartBadge.isVisible(),
                "Cart count is not displayed"
        );

        System.out.println("cartbadge visibel: "+cartBadge.isVisible());
        assertEquals(
                "1",
                cartBadge.innerText(),
                "Cart count is incorrect"
        );
        
        page.waitForTimeout(3000);//check the flow in ui
        System.out.println("cart count: "+cartBadge.innerText());
    }

	
	 
	 
   
	 @Test
	  @Order(1)
	    void TC_002_Add_Products_To_Cart_Checkproductdetails() throws InterruptedException {

	        Allure.step("Open SauceDemo URL");

	        page.navigate("https://www.saucedemo.com/");


	        Allure.step("Login with valid credentials");

	        login();


	        


	        Allure.step("Display total product count with names");

	        Locator products = page.locator("[data-test='inventory-item']");
	        
	        products.first().waitFor();
	        System.out.println("Product count: "+products.count());

	        for (int i = 0; i < products.count(); i++) {

	            Locator product = products.nth(i);

	            // Get product name
	            String productName =
	                    product.locator("[data-test='inventory-item-name']").innerText();

	            // Get product price
	            String price =
	                    product.locator("[data-test='inventory-item-price']").innerText();

	            // Add THIS product to cart
	            product.getByRole(
	                    AriaRole.BUTTON,
	                    new Locator.GetByRoleOptions().setName("Add to cart")
	            ).click();
	            
	            page.waitForTimeout(1000);

	            // Cart badge assertion
	            String actualCartCount =
	                    page.locator(".shopping_cart_badge").innerText();
                System.out.println("Cart count: "+actualCartCount);
	            // Click cart
	            page.locator(".shopping_cart_link").click();
	            
	            page.waitForTimeout(1000);

	            // Find THIS product in cart
	            Locator cartItem = page.locator(".cart_item")
	                    .filter(new Locator.FilterOptions().setHasText(productName));
	            Locator itemcount=page.locator("div.cart_item");
	            itemcount.first().waitFor();
	            System.out.println("Total items selected in cart: "+itemcount.count());
	            
	            // Quantity
	            String quantity =
	                    cartItem.locator(".cart_quantity").innerText();

	            // Description
	            String description =
	                    cartItem.locator(".inventory_item_name").innerText();

	            // Price
	            String cartPrice =
	                    cartItem.locator(".inventory_item_price").innerText();
	            System.out.println("Quantity : "+quantity);
	            System.out.println("Product item description: "+description);
	            System.out.println("cartPrice: "+cartPrice);

	            // Assertions
	            assertEquals("1", quantity);
	            assertEquals(productName, description);
	            assertEquals(price, cartPrice);
	            page.waitForTimeout(1000);

	            // Go back to inventory for next nth product
	            page.getByRole(
	                    AriaRole.BUTTON,
	                    new Page.GetByRoleOptions().setName("Continue Shopping")
	            ).click();
	           // page.goBack();
	        }
	        
	        }
	 
	    
	     
	     

	    
   

    @Test
    @Order(3)
    void TC_003_Add_To_Cart_CheckoutDetails() {

        Allure.step("Open SauceDemo URL");
        page.navigate("https://www.saucedemo.com/");

        Allure.step("Login with valid credentials");
        login();

        Allure.step("Display total product count with names");

        Locator productNames =
                page.locator(".inventory_item_name");
        
        productNames.first().waitFor();
        System.out.println(
                "Total Products: " + productNames.count()
        );
        
       
        for (String name : productNames.allTextContents()) {
            System.out.println("Product: " + name);
        }

        Allure.step("Add Sauce Labs Backpack to Cart");

        
        
        page.locator(
                "[data-test='add-to-cart-sauce-labs-bolt-t-shirt']"
        ).click();

        Allure.step("Click Cart logo");

        page.locator(".shopping_cart_link").click();
 
        page.waitForTimeout(1000);
        System.out.println("Page title: "+page.url());
        Allure.step("Click Checkout");

        page.getByRole(
                com.microsoft.playwright.options.AriaRole.BUTTON,
                new com.microsoft.playwright.Page.GetByRoleOptions()
                        .setName("Checkout")
        ).click();
        
        page.waitForTimeout(1000);
        System.out.println("Page title: "+page.url());

        Allure.step("Fill checkout information");

        page.getByPlaceholder("First Name")
                .fill("Test");
        page.waitForTimeout(1000);

        page.getByPlaceholder("Last Name")
                .fill("User");
        page.waitForTimeout(1000);

        page.getByPlaceholder("Zip/Postal Code")
                .fill("500001");
        page.waitForTimeout(1000);
        Allure.step("Click Continue");

        page.getByRole(
                com.microsoft.playwright.options.AriaRole.BUTTON,
                new com.microsoft.playwright.Page.GetByRoleOptions()
                        .setName("Continue")
        ).click();
        
        System.out.println("Page title: "+page.url());

        page.waitForTimeout(1000);
        Allure.step("Display payment, shipping, price total and total");

        Locator paymentInfo =
                page.locator("[data-test='payment-info-value']");

        Locator shippingInfo =
                page.locator("[data-test='shipping-info-value']");

        Locator subtotal =
                page.locator("[data-test='subtotal-label']");
        Locator tax =
                page.locator("[data-test='tax-label']");

        Locator total =
                page.locator("[data-test='total-label']");

        System.out.println(
                "Payment: " + paymentInfo.innerText()
        );

        System.out.println(
                "Shipping: " + shippingInfo.innerText()
        );

        System.out.println(
                "Summary subtotal: " + subtotal.innerText()
        );

        System.out.println(
                "Summary tax: " + tax.innerText()
        );
        System.out.println(
                "Summary Total: " + total.innerText()
        );
        
        

        Allure.step("Assert payment details");

        assertTrue(
                paymentInfo.isVisible(),
                "Payment information is not displayed"
        );

        Allure.step("Assert shipping details");

        assertTrue(
                shippingInfo.isVisible(),
                "Shipping information is not displayed"
        );

        Allure.step("Assert price total");

        assertTrue(
                subtotal.isVisible(),
                "Price total is not displayed"
        );

        Allure.step("Assert total");

        assertTrue(
                total.isVisible(),
                "Total amount is not displayed"
        );
    }

    

    @Test
    @Order(4)
    void TC_004_Add_To_Cart_SuccessfulFlow() {

        Allure.step("Open SauceDemo URL");
        page.navigate("https://www.saucedemo.com/");

        Allure.step("Login with valid credentials");
        login();

        Allure.step("Display total product count with names");

        Locator productNames =
                page.locator(".inventory_item_name");
        productNames.first().waitFor();
        System.out.println(
                "Total Products: " + productNames.count()
        );

        for (String name : productNames.allTextContents()) {
            System.out.println("Product: " + name);
        }

        Allure.step("Add Sauce Labs Backpack to Cart");
        
        page.locator(
                "[data-test='add-to-cart-sauce-labs-fleece-jacket']"
        ).click();
       
       
        page.waitForTimeout(1000);

        Allure.step("Click Cart logo");

        page.locator(".shopping_cart_link").click();
        page.waitForTimeout(1000);
        System.out.println("Page title: "+page.url());
        Allure.step("Click Checkout");

        page.getByRole(
                com.microsoft.playwright.options.AriaRole.BUTTON,
                new com.microsoft.playwright.Page.GetByRoleOptions()
                        .setName("Checkout")
        ).click();
        System.out.println("Page title: "+page.url());
        
        Allure.step("Fill checkout information");

        page.getByPlaceholder("First Name")
                .fill("Test");
        page.waitForTimeout(1000);

        page.getByPlaceholder("Last Name")
                .fill("User");
        page.waitForTimeout(1000);

        page.getByPlaceholder("Zip/Postal Code")
                .fill("500001");
        page.waitForTimeout(1000);
        Allure.step("Click Continue");

        page.getByRole(
                com.microsoft.playwright.options.AriaRole.BUTTON,
                new com.microsoft.playwright.Page.GetByRoleOptions()
                        .setName("Continue")
        ).click();
        page.waitForTimeout(1000);
        System.out.println("Page title: "+page.url());


        Allure.step("Display product quantity, description and price");
        Locator paymentInfo =
                page.locator("[data-test='payment-info-value']");

        Locator shippingInfo =
                page.locator("[data-test='shipping-info-value']");

        Locator subtotal =
                page.locator("[data-test='subtotal-label']");
        Locator tax =
                page.locator("[data-test='tax-label']");

        Locator total =
                page.locator("[data-test='total-label']");

        Allure.step("Display payment, shipping, price total and total");
        System.out.println(
                "Payment: " + paymentInfo.innerText()
        );

        System.out.println(
                "Shipping: " + shippingInfo.innerText()
        );

        System.out.println(
                "Summary subtotal: " + subtotal.innerText()
        );

        System.out.println(
                "Summary tax: " + tax.innerText()
        );
        System.out.println(
                "Summary Total: " + total.innerText()
        );
        
        
        page.waitForTimeout(1000);
        
        
        
        

       

        assertTrue(
                page.locator("[data-test='payment-info-value']").isVisible()
        );

        assertTrue(
                page.locator("[data-test='shipping-info-value']").isVisible()
        );

        assertTrue(
                page.locator("[data-test='subtotal-label']").isVisible()
        );

        assertTrue(
                page.locator("[data-test='total-label']").isVisible()
        );

        Allure.step("Click Finish");

        page.getByRole(
                com.microsoft.playwright.options.AriaRole.BUTTON,
                new com.microsoft.playwright.Page.GetByRoleOptions()
                        .setName("Finish")
        ).click();
        page.waitForTimeout(1000);

        Allure.step("Get page title");

        String title = page.title();

        System.out.println("Page Title: " + title);

        Allure.step("Get page information");

        String pageUrl = page.url();

        System.out.println("Page URL: " + pageUrl);

        String confirmationMessage =
                page.locator(".complete-header").innerText();

        System.out.println(
                "Confirmation Message: " + confirmationMessage
        );

        assertEquals(
                "https://www.saucedemo.com/checkout-complete.html",
                pageUrl,
                "Checkout complete URL is incorrect"
        );

        assertEquals(
                "Thank you for your order!",
                confirmationMessage,
                "Order confirmation message is incorrect"
        );
    }


   


}