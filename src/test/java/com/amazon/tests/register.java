package com.amazon.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class register {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testRegisterNewUser() {
        // Generate unique credentials for the test run
        String timestamp = String.valueOf(System.currentTimeMillis());
        String userName = "TestUser" + timestamp.substring(timestamp.length() - 4);
        String userEmail = "testuser" + timestamp + "@example.com";
        String password = "Test@12345";

        // 1. Navigate to the base URL
        driver.get("https://automationexercise.com/");
        System.out.println("Step 1: Navigated to Home Page.");

        // 2. Click on 'Signup / Login' button
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/login']"))).click();
        System.out.println("Step 2: Clicked Signup / Login.");

        // 3. Verify 'New User Signup!' is visible
        WebElement signupHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[text()='New User Signup!']")));
        assertTrue(signupHeader.isDisplayed(), "New User Signup header not visible.");
        System.out.println("Step 3: Verified Signup header.");

        // 4. Enter name and email address in the Signup form
        driver.findElement(By.xpath("//input[@data-qa='signup-name']")).sendKeys(userName);
        driver.findElement(By.xpath("//input[@data-qa='signup-email']")).sendKeys(userEmail);
        System.out.println("Step 4: Entered unique Name and Email.");

        // 5. Click 'Signup' button
        driver.findElement(By.xpath("//button[@data-qa='signup-button']")).click();
        System.out.println("Step 5: Clicked Signup button.");

        // 6. Verify 'ENTER ACCOUNT INFORMATION' is visible
        WebElement accountInfoHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2/b[text()='Enter Account Information']")));
        assertTrue(accountInfoHeader.isDisplayed(), "Account Information form did not load.");
        System.out.println("Step 6: Verified 'Enter Account Information' page.");

        // 7. Fill details: Title, Password, Date of birth
        driver.findElement(By.id("id_gender1")).click(); // Selects 'Mr.'
        driver.findElement(By.id("password")).sendKeys(password);

        // Date of Birth: 1 / January / 1990
        driver.findElement(By.id("days")).sendKeys("1");
        driver.findElement(By.id("months")).sendKeys("January");
        driver.findElement(By.id("years")).sendKeys("1990");

        // Select Checkboxes
        driver.findElement(By.id("newsletter")).click();
        driver.findElement(By.id("optin")).click();
        System.out.println("Step 7: Filled Account details.");

        // 8. Fill details: First name, Last name, Company, Address, Country, State, City, Zipcode, Mobile Number
        driver.findElement(By.id("first_name")).sendKeys("Test");
        driver.findElement(By.id("last_name")).sendKeys("User");
        driver.findElement(By.id("company")).sendKeys("AutomationCo");
        driver.findElement(By.id("address1")).sendKeys("123 Test Street");
        driver.findElement(By.id("country")).sendKeys("United States");
        driver.findElement(By.id("state")).sendKeys("California");
        driver.findElement(By.id("city")).sendKeys("Los Angeles");
        driver.findElement(By.id("zipcode")).sendKeys("90001");
        driver.findElement(By.id("mobile_number")).sendKeys("9876543210");
        System.out.println("Step 8: Filled Address details.");

        // 9. Click 'Create Account button'
        driver.findElement(By.xpath("//button[@data-qa='create-account']")).click();
        System.out.println("Step 9: Clicked 'Create Account'.");

        // 10. Verify that 'ACCOUNT CREATED!' is visible
        WebElement accountCreatedHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2/b[text()='Account Created!']")));
        assertTrue(accountCreatedHeader.isDisplayed(), "Account was not successfully created.");
        System.out.println("Step 10: Verified 'ACCOUNT CREATED!' success message.");

        // 11. Click 'Continue' button
        driver.findElement(By.xpath("//a[@data-qa='continue-button']")).click();
        System.out.println("Step 11: Clicked 'Continue'.");

        // 12. Verify 'Logged in as username' is visible (Optional Cleanup step: Delete Account)
        WebElement loggedInLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(), 'Logged in as')]")));
        assertTrue(loggedInLink.isDisplayed(), "User is not logged in after creation.");
        System.out.println("Step 12: Verified user is logged in.");
    }


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}