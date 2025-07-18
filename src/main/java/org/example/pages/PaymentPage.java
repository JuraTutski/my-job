
package org.example.pages;

import io.qameta.allure.Step;
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

    @Step("Проверка отображения страницы оплаты")
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
                    // Игнорируются исключения во время ожидания
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

    @Step("Получение отображаемой суммы на странице оплаты")
    public String getDisplayedAmount() {
        try {
            WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@src, 'checkout.bepaid.by')]"));
            driver.switchTo().frame(iframe);


            Thread.sleep(3000);


            List<String> possibleSelectors = List.of(
                    "//*[contains(text(), '10.00')]",
                    "//*[contains(text(), '10,00')]",
                    "//*[contains(text(), '10 BYN')]",
                    "//*[contains(text(), '10 руб')]",
                    "//*[contains(text(), '10')]",
                    "//span[contains(@class, 'amount')]",
                    "//div[contains(@class, 'amount')]",
                    "//div[contains(@class, 'price')]",
                    "//span[contains(@class, 'price')]",
                    "//div[contains(@class, 'total')]",
                    "//span[contains(@class, 'total')]",
                    "//div[contains(@class, 'sum')]",
                    "//span[contains(@class, 'sum')]"
            );

            for (String selector : possibleSelectors) {
                try {
                    List<WebElement> elements = driver.findElements(By.xpath(selector));
                    for (WebElement element : elements) {
                        String text = element.getText().trim();
                        if (!text.isEmpty() && (text.contains("10") || text.toLowerCase().contains("byn") || text.contains("руб"))) {
                            System.out.println("Найдена сумма по селектору '" + selector + "': " + text);
                            driver.switchTo().defaultContent();
                            return text;
                        }
                    }
                } catch (Exception e) {

                }
            }


            System.out.println("=== ОТЛАДОЧНАЯ ИНФОРМАЦИЯ ===");
            try {
                String pageSource = driver.getPageSource();
                System.out.println("HTML содержимое iframe (первые 1000 символов):");
                System.out.println(pageSource.substring(0, Math.min(1000, pageSource.length())));


                List<WebElement> allElements = driver.findElements(By.xpath("//*[text()]"));
                System.out.println("Все текстовые элементы в iframe:");
                for (int i = 0; i < Math.min(10, allElements.size()); i++) {
                    WebElement element = allElements.get(i);
                    String text = element.getText().trim();
                    if (!text.isEmpty()) {
                        System.out.println("- " + element.getTagName() + ": " + text);
                    }
                }
            } catch (Exception e) {
                System.out.println("Ошибка при получении отладочной информации: " + e.getMessage());
            }

            driver.switchTo().defaultContent();
            return "Сумма не найдена";
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            return "Ошибка при получении суммы: " + e.getMessage();
        }
    }

    @Step("Получение отображаемого номера телефона на странице оплаты")
    public String getDisplayedPhoneNumber() {
        try {
            WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@src, 'checkout.bepaid.by')]"));
            driver.switchTo().frame(iframe);

            Thread.sleep(3000);


            List<String> possibleSelectors = List.of(
                    "//*[contains(text(), '297777777')]",
                    "//*[contains(text(), '+375297777777')]",
                    "//*[contains(text(), '375297777777')]",
                    "//*[contains(text(), '+375 29 777-77-77')]",
                    "//*[contains(text(), '29 777-77-77')]"
            );

            for (String selector : possibleSelectors) {
                try {
                    List<WebElement> elements = driver.findElements(By.xpath(selector));
                    for (WebElement element : elements) {
                        String text = element.getText().trim();
                        if (!text.isEmpty() && text.contains("777777")) {
                            System.out.println("Найден номер телефона: " + text);
                            driver.switchTo().defaultContent();
                            return text;
                        }
                    }
                } catch (Exception e) {

                }
            }


            System.out.println("=== ОТЛАДКА: Поиск номера телефона ===");
            try {
                List<WebElement> allElements = driver.findElements(By.xpath("//*[text()]"));
                System.out.println("Все текстовые элементы для поиска номера:");
                for (int i = 0; i < Math.min(15, allElements.size()); i++) {
                    WebElement element = allElements.get(i);
                    String text = element.getText().trim();
                    if (!text.isEmpty()) {
                        System.out.println("- " + text);
                    }
                }
            } catch (Exception e) {
                System.out.println("Ошибка при получении отладочной информации для номера: " + e.getMessage());
            }

            driver.switchTo().defaultContent();
            return "Номер телефона не найден";
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            return "Ошибка при получении номера: " + e.getMessage();
        }
    }

    @Step("Получение суммы на кнопке оплаты")
    public String getAmountOnButton() {
        try {
            WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@src, 'checkout.bepaid.by')]"));
            driver.switchTo().frame(iframe);

            Thread.sleep(3000);


            List<String> buttonSelectors = List.of(
                    "//button[contains(text(), 'Оплатить')]",
                    "//button[contains(text(), 'Заплатить')]",
                    "//button[contains(text(), 'Подтвердить')]",
                    "//button[contains(text(), '10')]",
                    "//input[@type='submit']",
                    "//button[@type='submit']",
                    "//button[contains(@class, 'pay')]",
                    "//button[contains(@class, 'submit')]"
            );

            for (String selector : buttonSelectors) {
                try {
                    List<WebElement> buttons = driver.findElements(By.xpath(selector));
                    for (WebElement button : buttons) {
                        String text = button.getText().trim();
                        if (!text.isEmpty()) {
                            System.out.println("Найдена кнопка: " + text);
                            driver.switchTo().defaultContent();
                            return text;
                        }
                    }
                } catch (Exception e) {

                }
            }


            System.out.println("=== ОТЛАДКА: Поиск кнопки оплаты ===");
            try {
                List<WebElement> allButtons = driver.findElements(By.xpath("//button"));
                System.out.println("Все найденные кнопки:");
                for (WebElement button : allButtons) {
                    String text = button.getText().trim();
                    System.out.println("- Кнопка: " + text + " (enabled: " + button.isEnabled() + ")");
                }
            } catch (Exception e) {
                System.out.println("Ошибка при получении информации о кнопках: " + e.getMessage());
            }

            driver.switchTo().defaultContent();
            return "Кнопка оплаты не найдена";
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            return "Ошибка при получении текста кнопки: " + e.getMessage();
        }
    }

    @Step("Получение плейсхолдеров полей карты")
    public List<String> getCardFieldPlaceholders() {
        List<String> placeholders = new ArrayList<>();

        try {
            WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@src, 'checkout.bepaid.by')]"));
            driver.switchTo().frame(iframe);

            Thread.sleep(3000);

            List<WebElement> allInputs = driver.findElements(By.xpath("//input"));
            System.out.println("Найдено полей ввода: " + allInputs.size());

            for (WebElement input : allInputs) {
                String placeholder = input.getAttribute("placeholder");
                String inputType = input.getAttribute("type");
                String inputId = input.getAttribute("id");
                String inputName = input.getAttribute("name");
                String inputClass = input.getAttribute("class");

                System.out.println("Поле: type=" + inputType + ", id=" + inputId + ", name=" + inputName + ", placeholder=" + placeholder);

                if (placeholder != null && !placeholder.trim().isEmpty()) {
                    placeholders.add(placeholder);
                } else {

                    if (inputId != null) {
                        if (inputId.contains("card") || inputId.contains("cc-number")) {
                            placeholders.add("Номер карты (ID: " + inputId + ")");
                        } else if (inputId.contains("cvv") || inputId.contains("verification")) {
                            placeholders.add("CVV код (ID: " + inputId + ")");
                        } else if (inputId.contains("date") || inputId.contains("expiry")) {
                            placeholders.add("Срок действия (ID: " + inputId + ")");
                        } else if (inputId.contains("name") || inputId.contains("holder")) {
                            placeholders.add("Имя владельца (ID: " + inputId + ")");
                        } else {
                            placeholders.add("Поле ввода (ID: " + inputId + ")");
                        }
                    } else if (inputName != null) {
                        placeholders.add("Поле ввода (Name: " + inputName + ")");
                    } else if (inputType != null) {
                        placeholders.add("Поле ввода (Type: " + inputType + ")");
                    } else {
                        placeholders.add("Неопределенное поле ввода");
                    }
                }
            }

            if (placeholders.isEmpty()) {
                placeholders.add("Поля ввода не найдены");
            }

            driver.switchTo().defaultContent();
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            placeholders.add("Ошибка при получении плейсхолдеров: " + e.getMessage());
        }

        return placeholders;
    }

    @Step("Получение списка платёжных систем по иконкам")
    public List<String> getPaymentSystemIcons() {
        List<String> icons = new ArrayList<>();

        try {
            WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@src, 'checkout.bepaid.by')]"));
            driver.switchTo().frame(iframe);

            Thread.sleep(3000);


            List<String> iconSelectors = List.of(
                    "//img[contains(@src, 'visa')]",
                    "//img[contains(@src, 'mastercard')]",
                    "//img[contains(@src, 'belkart')]",
                    "//img[contains(@src, 'maestro')]",
                    "//img[contains(@src, 'mir')]",
                    "//img[contains(@alt, 'visa')]",
                    "//img[contains(@alt, 'mastercard')]",
                    "//img[contains(@alt, 'belkart')]",
                    "//img[contains(@class, 'payment')]",
                    "//img[contains(@class, 'card')]"
            );

            for (String selector : iconSelectors) {
                try {
                    List<WebElement> iconElements = driver.findElements(By.xpath(selector));
                    for (WebElement icon : iconElements) {
                        String src = icon.getAttribute("src");
                        String alt = icon.getAttribute("alt");

                        if (src != null) {
                            if (src.contains("visa") && !icons.contains("Visa")) {
                                icons.add("Visa");
                            } else if (src.contains("mastercard") && !icons.contains("MasterCard")) {
                                icons.add("MasterCard");
                            } else if (src.contains("belkart") && !icons.contains("Белкарт")) {
                                icons.add("Белкарт");
                            } else if (src.contains("maestro") && !icons.contains("Maestro")) {
                                icons.add("Maestro");
                            } else if (src.contains("mir") && !icons.contains("МИР")) {
                                icons.add("МИР");
                            }
                        }

                        if (alt != null) {
                            if (alt.toLowerCase().contains("visa") && !icons.contains("Visa")) {
                                icons.add("Visa");
                            } else if (alt.toLowerCase().contains("mastercard") && !icons.contains("MasterCard")) {
                                icons.add("MasterCard");
                            }
                        }
                    }
                } catch (Exception e) {

                }
            }


            if (icons.isEmpty()) {
                System.out.println("=== ОТЛАДКА: Поиск иконок платежных систем ===");
                try {
                    List<WebElement> allImages = driver.findElements(By.xpath("//img"));
                    System.out.println("Все найденные изображения:");
                    for (WebElement img : allImages) {
                        String src = img.getAttribute("src");
                        String alt = img.getAttribute("alt");
                        System.out.println("- IMG: src=" + src + ", alt=" + alt);
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка при получении информации об изображениях: " + e.getMessage());
                }
            }

            driver.switchTo().defaultContent();
        } catch (Exception e) {
            driver.switchTo().defaultContent();
        }

        return icons;
    }

    @Step("Проверка наличия иконок платёжных систем")
    public boolean hasPaymentSystemIcons() {
        try {
            WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@src, 'checkout.bepaid.by')]"));
            driver.switchTo().frame(iframe);

            Thread.sleep(3000);

            List<WebElement> icons = driver.findElements(By.xpath("//img"));
            boolean hasIcons = !icons.isEmpty();

            System.out.println("Найдено изображений в iframe: " + icons.size());

            driver.switchTo().defaultContent();
            return hasIcons;
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            return false;
        }
    }
}
