package org.example.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TopUpSection {
    private WebDriver driver;
    private WebDriverWait wait;
    private WebElement sectionRoot;

    public TopUpSection(WebDriver driver, WebElement sectionRoot) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.sectionRoot = sectionRoot;
    }

    @Step("Выбор типа услуги: {serviceType}")
    public void selectServiceType(String serviceType) {
        WebElement currentSelection = sectionRoot.findElement(
                By.xpath(".//button[contains(@class, 'select__header')]//span[@class='select__now']")
        );

        if (currentSelection.getText().equals(serviceType)) {
            System.out.println("Тип услуги '" + serviceType + "' уже выбран");
            return;
        }

        WebElement dropdownButton = sectionRoot.findElement(
                By.xpath(".//button[contains(@class, 'select__header')]")
        );
        dropdownButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(".//ul[@class='select__list']")
        ));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement option = sectionRoot.findElement(
                By.xpath(".//li[@class='select__item']//p[contains(text(), '" + serviceType + "')]")
        );

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", option);

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(".//input[@placeholder]")
        ));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step("Получение плейсхолдеров для типа услуги: {serviceType}")
    public List<String> getFieldPlaceholdersForServiceType(String serviceType) {
        selectServiceType(serviceType);

        List<String> placeholders = new ArrayList<>();

        try {
            Thread.sleep(1000);


            List<WebElement> inputs = sectionRoot.findElements(By.xpath(".//input[@placeholder]"));

            for (WebElement input : inputs) {
                if (input.isEnabled()) {
                    String placeholder = input.getAttribute("placeholder");
                    if (placeholder != null && !placeholder.trim().isEmpty()) {
                        placeholders.add(placeholder);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return placeholders;
    }

    @Step("Ввод номера телефона: {phoneNumber}")
    public void fillPhoneNumber(String phoneNumber) {
        try {
            Thread.sleep(1000);

            WebElement phoneInput = sectionRoot.findElement(
                    By.xpath(".//input[@id='connection-phone']")
            );

            if (phoneInput.isEnabled()) {
                phoneInput.clear();
                phoneInput.sendKeys(phoneNumber);
                System.out.println("Номер телефона заполнен: " + phoneNumber);
            } else {
                throw new RuntimeException("Поле для номера телефона недоступно");
            }
        } catch (Exception e) {
            try {
                WebElement phoneInput = sectionRoot.findElement(
                        By.xpath(".//input[@placeholder='Номер телефона']")
                );

                if (phoneInput.isEnabled()) {
                    phoneInput.clear();
                    phoneInput.sendKeys(phoneNumber);
                    System.out.println("Номер телефона заполнен (по placeholder): " + phoneNumber);
                } else {
                    throw new RuntimeException("Поле для номера телефона недоступно");
                }
            } catch (Exception ex) {
                throw new RuntimeException("Поле для номера телефона не найдено: " + ex.getMessage());
            }
        }
    }

    @Step("Ввод суммы пополнения: {amount}")
    public void fillAmount(String amount) {
        try {
            Thread.sleep(1000);

            WebElement amountInput = sectionRoot.findElement(
                    By.xpath(".//input[@id='connection-sum']")
            );

            if (amountInput.isEnabled()) {
                amountInput.clear();
                amountInput.sendKeys(amount);
                System.out.println("Сумма заполнена: " + amount);
            } else {
                throw new RuntimeException("Поле для суммы недоступно");
            }
        } catch (Exception e) {
            try {
                WebElement amountInput = sectionRoot.findElement(
                        By.xpath(".//input[@placeholder='Сумма']")
                );

                if (amountInput.isEnabled()) {
                    amountInput.clear();
                    amountInput.sendKeys(amount);
                    System.out.println("Сумма заполнена (по placeholder): " + amount);
                } else {
                    throw new RuntimeException("Поле для суммы недоступно");
                }
            } catch (Exception ex) {
                throw new RuntimeException("Поле для суммы не найдено: " + ex.getMessage());
            }
        }
    }

    @Step("Нажатие кнопки 'Продолжить'")
    public void clickContinueButton() {
        try {
            Thread.sleep(1000);

            WebElement continueButton = sectionRoot.findElement(
                    By.xpath(".//button[contains(text(), 'Продолжить')]")
            );

            JavascriptExecutor js = (JavascriptExecutor) driver;

            if (continueButton.isEnabled()) {
                js.executeScript("arguments[0].click();", continueButton);
                System.out.println("Кнопка 'Продолжить' нажата");
            } else {
                throw new RuntimeException("Кнопка 'Продолжить' недоступна");
            }
        } catch (Exception e) {
            throw new RuntimeException("Кнопка 'Продолжить' не найдена: " + e.getMessage());
        }
    }


    @Step("Проверка, что кнопка 'Продолжить' активна")
    public boolean isContinueButtonEnabled() {
        List<WebElement> continueButtons = sectionRoot.findElements(
                By.xpath(".//button[contains(text(), 'Продолжить')]")
        );

        for (WebElement button : continueButtons) {
            if (button.isEnabled()) {
                return button.getAttribute("disabled") == null;
            }
        }

        return false;
    }

    @Step("Получение всех плейсхолдеров в секции")
    public List<String> getAllPlaceholderTexts() {
        try {
            Thread.sleep(1000);

            List<WebElement> inputs = sectionRoot.findElements(By.xpath(".//input[@placeholder]"));
            List<String> placeholders = new ArrayList<>();

            for (WebElement input : inputs) {
                if (input.isEnabled()) {
                    String placeholder = input.getAttribute("placeholder");
                    if (placeholder != null && !placeholder.isEmpty()) {
                        placeholders.add(placeholder);
                    }
                }
            }

            return placeholders;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Step("Получение всех типов услуг в выпадающем списке")
    public List<String> getAvailableServiceTypes() {
        List<String> serviceTypes = new ArrayList<>();

        WebElement dropdownButton = sectionRoot.findElement(
                By.xpath(".//button[contains(@class, 'select__header')]")
        );
        dropdownButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(".//ul[@class='select__list']")
        ));

        List<WebElement> options = sectionRoot.findElements(
                By.xpath(".//li[@class='select__item']//p[@class='select__option']")
        );

        for (WebElement option : options) {
            serviceTypes.add(option.getText());
        }

        dropdownButton.click();

        return serviceTypes;
    }
}
