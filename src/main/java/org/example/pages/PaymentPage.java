

package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PaymentPage extends BasePage {

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPaymentPageDisplayed() {
        try {
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(30));

            System.out.println("Ожидаем появления iframe...");
            WebElement iframe = longWait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//iframe[contains(@src, 'checkout.bepaid.by')]")
            ));

            System.out.println("Iframe найден: " + iframe.getAttribute("src"));

            System.out.println("Ждем видимости iframe...");
            boolean iframeVisible = false;
            for (int i = 0; i < 60; i++) {
                try {
                    Thread.sleep(500);
                    if (iframe.isDisplayed()) {
                        iframeVisible = true;
                        System.out.println("Iframe стал видимым через " + (i * 0.5) + " секунд");
                        break;
                    }
                } catch (Exception e) {

                }
            }

            driver.switchTo().frame(iframe);
            System.out.println("Переключились в iframe");

            longWait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//form")),
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//input")),
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//button")),
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class]"))
            ));

            System.out.println("Контент внутри iframe загружен");
            driver.switchTo().defaultContent();

            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при проверке страницы оплаты: " + e.getMessage());
            driver.switchTo().defaultContent();
            return false;
        }
    }

    public String getDisplayedAmount() {
        try {
            WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@src, 'checkout.bepaid.by')]"));
            driver.switchTo().frame(iframe);

            Thread.sleep(2000);


            List<WebElement> allElements = driver.findElements(By.xpath("//*[contains(text(), '10') or contains(text(), 'руб')]"));

            for (WebElement element : allElements) {
                String text = element.getText().trim();
                if (text.contains("10") && (text.contains("руб") || text.contains("BYN"))) {
                    driver.switchTo().defaultContent();
                    return text;
                }
            }

            driver.switchTo().defaultContent();
            return "Сумма не найдена";
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            return "Ошибка при получении суммы: " + e.getMessage();
        }
    }

    public String getDisplayedPhoneNumber() {
        try {
            WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@src, 'checkout.bepaid.by')]"));
            driver.switchTo().frame(iframe);

            Thread.sleep(2000);

            List<WebElement> allElements = driver.findElements(By.xpath("//*[contains(text(), '297777777') or contains(text(), '+375297777777')]"));

            for (WebElement element : allElements) {
                String text = element.getText().trim();
                if (text.contains("297777777")) {
                    driver.switchTo().defaultContent();
                    return text;
                }
            }

            driver.switchTo().defaultContent();
            return "Номер телефона не найден";
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            return "Ошибка при получении номера: " + e.getMessage();
        }
    }

    public String getAmountOnButton() {
        try {
            WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@src, 'checkout.bepaid.by')]"));
            driver.switchTo().frame(iframe);

            Thread.sleep(2000);

            List<WebElement> buttons = driver.findElements(By.xpath("//button"));

            for (WebElement button : buttons) {
                String text = button.getText().trim();
                if (text.contains("10") || text.contains("Оплатить")) {
                    driver.switchTo().defaultContent();
                    return text;
                }
            }

            driver.switchTo().defaultContent();
            return "Кнопка оплаты не найдена";
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            return "Ошибка при получении текста кнопки: " + e.getMessage();
        }
    }

    public List<String> getCardFieldPlaceholders() {
        List<String> placeholders = new ArrayList<>();

        try {
            WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@src, 'checkout.bepaid.by')]"));
            driver.switchTo().frame(iframe);

            Thread.sleep(3000);

            List<WebElement> allInputs = driver.findElements(By.xpath("//input"));

            for (WebElement input : allInputs) {
                String placeholder = input.getAttribute("placeholder");
                String inputType = input.getAttribute("type");
                String inputId = input.getAttribute("id");
                String inputName = input.getAttribute("name");

                if (placeholder != null && !placeholder.trim().isEmpty()) {
                    placeholders.add(placeholder);
                } else {
                    if ("cc-number".equals(inputId)) {
                        placeholders.add("Номер карты (определено по ID)");
                    } else if ("verification_value".equals(inputName)) {
                        placeholders.add("CVV/CVC код (определено по name)");
                    } else if ("tel".equals(inputType)) {
                        if (inputId != null && inputId.contains("date")) {
                            placeholders.add("Срок действия (определено по типу)");
                        } else {
                            placeholders.add("Поле ввода номера (определено по типу)");
                        }
                    } else if ("text".equals(inputType)) {
                        placeholders.add("Текстовое поле (определено по типу)");
                    }
                }
            }

            if (placeholders.isEmpty()) {
                placeholders.add("Найдено полей: " + allInputs.size());
                for (int i = 0; i < allInputs.size(); i++) {
                    WebElement input = allInputs.get(i);
                    placeholders.add("Поле " + (i + 1) + ": " + input.getAttribute("type") +
                            " (ID: " + input.getAttribute("id") + ", Name: " + input.getAttribute("name") + ")");
                }
            }

            driver.switchTo().defaultContent();
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            placeholders.add("Ошибка при получении плейсхолдеров: " + e.getMessage());
        }

        return placeholders;
    }

    public List<String> getPaymentSystemIcons() {
        List<String> icons = new ArrayList<>();

        try {
            WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@src, 'checkout.bepaid.by')]"));
            driver.switchTo().frame(iframe);

            Thread.sleep(2000);


            List<WebElement> iconElements = driver.findElements(By.xpath("//img[contains(@src, 'payment-icons')]"));

            for (WebElement icon : iconElements) {
                String src = icon.getAttribute("src");
                if (src != null) {
                    if (src.contains("visa")) {
                        icons.add("Visa");
                    } else if (src.contains("mastercard")) {
                        icons.add("MasterCard");
                    } else if (src.contains("belkart")) {
                        icons.add("Белкарт");
                    } else if (src.contains("maestro")) {
                        icons.add("Maestro");
                    } else if (src.contains("mir")) {
                        icons.add("МИР");
                    }
                }
            }

            driver.switchTo().defaultContent();
        } catch (Exception e) {
            driver.switchTo().defaultContent();
        }

        return icons;
    }

    public boolean hasPaymentSystemIcons() {
        try {
            WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@src, 'checkout.bepaid.by')]"));
            driver.switchTo().frame(iframe);

            Thread.sleep(2000);
            List<WebElement> icons = driver.findElements(By.xpath("//img[contains(@src, 'payment-icons')]"));
            boolean hasIcons = !icons.isEmpty();

            driver.switchTo().defaultContent();
            return hasIcons;
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            return false;
        }
    }
}
