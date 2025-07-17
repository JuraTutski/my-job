package org.example.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    protected void handleCookieBanner() {
        try {
            WebElement cookieButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector(".cookie__cancel"))
            );
            cookieButton.click();
        } catch (Exception e) {
            System.out.println("Cookie banner не найден или уже закрыт");
        }
    }

}

