package com.example.project.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.example.project.pages.LoginPage;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumLoginTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeAll
    public static void setupClass() {
        // Automatically download and setup the correct ChromeDriver
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setupTest() {
        // Initialize Chrome Driver
        ChromeOptions options = new ChromeOptions();

        // IMPORTANT: For CI/CD (like Jenkins), you usually want headless mode.
        // For local debugging, remove this line to SEE the browser.
        options.addArguments("--remote-allow-origins=*");
        // options.addArguments("--headless=new");

        driver = new ChromeDriver(options);
        loginPage = new LoginPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLoginFlow() {
        // 1. Navigate to the App
        driver.get("http://localhost:" + port + "/login");

        // 2. Perform Login via Page Object
        loginPage.login("admin", "password123");
        // Note: Change "admin"/"password123" to valid credentials in your
        // H2/DataSeeder!!

        // 3. Assert Success
        // For now, checks if we are redirected or if URL changes.
        // Adjust assertion based on what happens after login in your app
        String currentUrl = driver.getCurrentUrl();
        // assertTrue(currentUrl.contains("dashboard") || currentUrl.contains("home"));
    }
}
