package com.amazon.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Automation {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void setup() {
        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        // Initialize WebDriverWait for explicit waits
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testAddProductToCartAndCheckout() throws InterruptedException {
        // Generate unique credentials for the test run
        String timestamp = String.valueOf(System.currentTimeMillis());
        String userName = "TestUser" + timestamp.substring(timestamp.length() - 4);
        String userEmail = "testuser" + timestamp + "@example.com";
        String password = "Test@12345";

        // 1. Navigate to the base URL
        driver.get("https://automationexercise.com/");

        // --- ADD PRODUCT TO CART ---

        // 2. Click "Products" menu
        System.out.println("Step 2: Clicking Products menu.");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/products']"))).click();

        // 3. Click first product "View Product"
        System.out.println("Step 3: Clicking 'View Product' for the first item.");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[contains(text(),'View Product')])[1]"))).click();

        // 4. On product detail page, click "Add to cart" button
        System.out.println("Step 4: Clicking 'Add to cart' button.");
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.btn.btn-default.cart")));
        addToCartBtn.click();

        // 5. Wait for modal popup and click "View Cart"
        System.out.println("Step 5: Clicking 'View Cart' in the confirmation modal.");
        WebElement viewCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//u[contains(text(),'View Cart')]")));
        viewCartBtn.click();

        // 6. Verify product is added to cart
        System.out.println("Step 6: Verifying product is in the cart.");
        WebElement cartItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td[@class='cart_description']//a[text()='Blue Top']")));
        assertTrue(cartItem.isDisplayed(), "Product was not added to the cart.");
        System.out.println("Verification successful: Product is displayed in the cart.");
    }

    @AfterEach
    public void tearDown() {
        // Close the browser after each test
        if (driver != null) {
            driver.quit();
        }
    }
}