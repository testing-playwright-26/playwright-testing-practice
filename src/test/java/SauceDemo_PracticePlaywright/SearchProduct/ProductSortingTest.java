package SauceDemo_PracticePlaywright.SearchProduct;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

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

        Allure.step("Select Name A-Z sorting");
        page.locator("[data-test='product-sort-container']")
                .selectOption("az");

        Allure.step("Display total product count and names");

        Locator productNames = page.locator(".inventory_item_name");

        int productCount = productNames.count();

        System.out.println("Total Products: " + productCount);

        List<String> actualNames = productNames.allTextContents();

        for (String name : actualNames) {
            System.out.println("Product: " + name);
        }

        Allure.step("Assert products are in alphabetical A-Z order");

        List<String> expectedNames = new ArrayList<>(actualNames);
        Collections.sort(expectedNames);

        assertEquals(
                expectedNames,
                actualNames,
                "Products are not sorted in A-Z order"
        );
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

        Allure.step("Select Name Z-A sorting");
        page.locator("[data-test='product-sort-container']")
                .selectOption("za");

        Allure.step("Display total product count and names");

        Locator productNames = page.locator(".inventory_item_name");

        int productCount = productNames.count();

        System.out.println("Total Products: " + productCount);

        List<String> actualNames = productNames.allTextContents();

        for (String name : actualNames) {
            System.out.println("Product: " + name);
        }

        Allure.step("Assert products are in alphabetical Z-A order");

        List<String> expectedNames = new ArrayList<>(actualNames);
        expectedNames.sort(Collections.reverseOrder());

        assertEquals(
                expectedNames,
                actualNames,
                "Products are not sorted in Z-A order"
        );
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

        Allure.step("Select Price Low-High sorting");
        page.locator("[data-test='product-sort-container']")
                .selectOption("lohi");

        Allure.step("Display total product count and prices");

        Locator prices = page.locator(".inventory_item_price");

        int productCount = prices.count();

        System.out.println("Total Products: " + productCount);

        List<Double> actualPrices = getPrices(prices);

        for (Double price : actualPrices) {
            System.out.println("Price: $" + price);
        }

        Allure.step("Assert products are sorted by price Low-High");

        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);

        assertEquals(
                expectedPrices,
                actualPrices,
                "Products are not sorted Price Low-High"
        );
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

        Allure.step("Select Price High-Low sorting");
        page.locator("[data-test='product-sort-container']")
                .selectOption("hilo");

        Allure.step("Display total product count and prices");

        Locator prices = page.locator(".inventory_item_price");

        int productCount = prices.count();

        System.out.println("Total Products: " + productCount);

        List<Double> actualPrices = getPrices(prices);

        for (Double price : actualPrices) {
            System.out.println("Price: $" + price);
        }

        Allure.step("Assert products are sorted by price High-Low");

        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        expectedPrices.sort(Collections.reverseOrder());

        assertEquals(
                expectedPrices,
                actualPrices,
                "Products are not sorted Price High-Low"
        );
    }


    private List<Double> getPrices(Locator priceLocator) {

        List<String> priceTexts = priceLocator.allTextContents();

        List<Double> prices = new ArrayList<>();

        for (String price : priceTexts) {

            String cleanPrice = price.replace("$", "").trim();

            prices.add(Double.parseDouble(cleanPrice));
        }

        return prices;
    }
}
