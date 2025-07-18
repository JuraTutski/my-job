package org.example.pages;


import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @Step("Обработка баннера cookie (если отображается)")

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
    protected void takeScreenshot(String description) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(" " + description, "image/png",
                    new ByteArrayInputStream(screenshot), "png");
        } catch (Exception e) {
            System.out.println("Ошибка скриншота: " + e.getMessage());
        }
    }


    protected void takeElementScreenshot(WebElement element, String elementName) {
        try {
            byte[] screenshot = element.getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(" " + elementName, "image/png",
                    new ByteArrayInputStream(screenshot), "png");
        } catch (Exception e) {
            System.out.println("Ошибка скриншота элемента: " + e.getMessage());
        }
    }

}
