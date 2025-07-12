package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MtsTests {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    protected void setUp(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    @AfterEach
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }

    }
          // №1

    @Test
    public void testTopUpBlockTitle(){
        driver.get("https://www.mts.by/");

        WebElement title = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                  By.xpath("//h2[contains(text(),'Онлайн пополнение')]")
                )
        );

        String actualText = title.getText().replace("\n", " ").trim();
        String expectedText = "Онлайн пополнение без комиссии";

        System.out.println("Найденный заголовок: [" + actualText+"]");

        assertEquals(expectedText, actualText, "Заголовок блока не совпадает с ожидаемым");
    }

          // №2

    @Test

    public void testPaymentLogosCount(){
        driver.get("https://www.mts.by/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='pay-section']//img")
        ));

        List<WebElement> logos = driver.findElements(
                By.xpath("//div[@id='pay-section']//img")
        );

        assertEquals(5, logos.size(), "Ожидалось 5 логотипов, но найдено: " + logos.size());

        for (WebElement logo : logos){
            System.out.println("Логотип: " + logo.getAttribute("alt"));
        }

    }

    // №3
    @Test
    public void testMoreAboutServiceLink(){
        driver.get("https://www.mts.by/");

        try {
            WebElement cookieButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector(".cookie__cancel"))
            );
            cookieButton.click();
        } catch (Exception e){
            System.out.println("Банер cookie не найден или уже закрыт");
        }

        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='pay-section']//a[contains(text(), 'Подробнее')]")
        ));

        String initialUrl = driver.getCurrentUrl();

        link.click();

        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(initialUrl)));

        String newUrl = driver.getCurrentUrl();

        System.out.println("Переход выполнен, новая страница: " + newUrl);
        assertFalse(newUrl.equals(initialUrl), "Переход по ссылке не выполнен");


    }
    // №4
    @Test
    public void testTopUpFormForMobileService(){
        driver.get("https://www.mts.by/");

        try {
            WebElement cookieButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector(".cookie__cancel"))
            );
            cookieButton.click();
        } catch (Exception e){
            System.out.println("Банер cookie не найден или уже закрыт");
        }

        WebElement topUpBlock = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='pay-section']")
        ));

        WebElement mobileTab = topUpBlock.findElement(
                By.xpath(".//button[.//span[contains(text(), 'Услуги связи')]]")
        );
        mobileTab.click();

        WebElement phoneInput = topUpBlock.findElement(
                By.xpath(".//input[@placeholder='Номер телефона']")
        );
        phoneInput.sendKeys("297777777");

        WebElement amountInput = topUpBlock.findElement(
                By.xpath(".//input[@placeholder='Сумма']")
        );
        amountInput.sendKeys("10");

        WebElement submitButton = topUpBlock.findElement(
                By.xpath(".//button[contains(text(), 'Продолжить')]")
        );
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));

        boolean isDisabled = submitButton.getAttribute("disabled") != null;
        System.out.println("Кнопка доступна: " + !isDisabled);

        assertFalse(isDisabled, "Кнопка 'Продолжить' должна быть активной после заполнения формы");


    }

}
