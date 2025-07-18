package org.example.pages;

import io.qameta.allure.Step;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class MainPage extends BasePage {

    @FindBy(xpath = "//h2[contains(text(),'Онлайн пополнение')]")
    private WebElement topUpTitle;

    @FindBy(xpath = "//div[@id='pay-section']//img")
    private List<WebElement> paymentLogos;

    @FindBy(xpath = "//div[@id='pay-section']//a[contains(text(), 'Подробнее')]")
    private WebElement moreAboutServiceLink;

    @FindBy(xpath = "//div[@id='pay-section']")
    private WebElement topUpSection;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открытие главной страницы МТС и закрытие баннера cookies")
    public void open() {
        driver.get("https://www.mts.by/");
        handleCookieBanner();
    }

    @Step("Получение заголовка блока 'Онлайн пополнение'")
    public String getTopUpTitle() {
        WebElement title = wait.until(ExpectedConditions.visibilityOf(topUpTitle));
        return title.getText().replace("\n", " ").trim();
    }

    @Step("Получение количества логотипов платёжных систем")
    public int getPaymentLogosCount() {
        wait.until(ExpectedConditions.visibilityOfAllElements(paymentLogos));
        return paymentLogos.size();
    }

    @Step("Получение alt-текста всех логотипов платёжных систем")
    public List<String> getPaymentLogosAltTexts() {
        return paymentLogos.stream()
                .map(logo -> logo.getAttribute("alt"))
                .collect(Collectors.toList());
    }

    @Step("Клик по ссылке 'Подробнее о сервисе'")
    public void clickMoreAboutServiceLink() {
        wait.until(ExpectedConditions.elementToBeClickable(moreAboutServiceLink)).click();
    }

    @Step("Получение объекта секции пополнения")
    public TopUpSection getTopUpSection() {
        wait.until(ExpectedConditions.visibilityOf(topUpSection));
        return new TopUpSection(driver, topUpSection);
    }

}
