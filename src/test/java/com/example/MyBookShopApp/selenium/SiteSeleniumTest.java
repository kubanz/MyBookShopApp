package com.example.MyBookShopApp.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SiteSeleniumTest {

    private static RemoteWebDriver driver;

    @BeforeAll
    static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\kzamirbekov\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    public void testAllPages() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.callPage()
                .goToGenres()
                .pause()
                .goToGenre()
                .pause()
                .goToBookFromGenre()
                .pause()
                .goToNews()
                .pause()
                .goToPopular()
                .pause()
                .goToBookFromPopular()
                .pause()
                .goToAuthors()
                .pause()
                .goToAuthor()
                .pause()
                .goToAllBooksFromAuthor()
                .pause()
                .goToBookFromAuthor()
                .pause()
                .callPage()
                .pause();

        assertTrue(driver.getPageSource().contains("BOOKSHOP"));


    }

}
