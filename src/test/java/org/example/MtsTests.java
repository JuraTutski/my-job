package org.example;

import io.qameta.allure.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.example.pages.MainPage;
import org.example.pages.PaymentPage;
import org.example.pages.TopUpSection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.example.pages.AllureScreenshotExtension;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({AllureScreenshotExtension.class})
@Epic("Тестирование сайта МТС")
@Feature("Онлайн пополнение")
@Owner("QA Team")
public class MtsTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;

    @BeforeEach
    @Step("Инициализация WebDriver и настройка тестовой среды")
    protected void setUp(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        mainPage = new MainPage(driver);


        Allure.addAttachment("Информация о браузере", "text/plain",
                "Browser: Chrome\nDriver: ChromeDriver\nTimeout: 10 секунд\nURL: https://www.mts.by/", "txt");
    }

    @AfterEach
    @Step("Закрытие браузера и очистка ресурсов")
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }

    @Test
    @Feature("Плейсхолдеры полей")
    @Story("Проверка плейсхолдеров для всех типов услуг")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверяется наличие обязательных плейсхолдеров для каждого типа услуги: сумма и email")
    @Issue("MTS-001")
    @TmsLink("TC-001")
    public void testAllServiceTypesPlaceholders() {
        Allure.step("Начало теста плейсхолдеров для всех типов услуг", () -> {
            System.out.println("=== Тест плейсхолдеров для всех типов услуг ===");
        });

        mainPage.open();
        TopUpSection topUpSection = mainPage.getTopUpSection();

        List<String> serviceTypes = Allure.step("Получение списка доступных типов услуг", () -> {
            List<String> types = topUpSection.getAvailableServiceTypes();
            System.out.println("Доступные типы услуг: " + types);
            return types;
        });

        Allure.addAttachment("Доступные типы услуг", "text/plain",
                String.join(", ", serviceTypes), "txt");

        String[] expectedServices = {"Услуги связи", "Домашний интернет", "Рассрочка", "Задолженность"};

        Allure.step("Проверка каждого типа услуги на наличие обязательных полей", () -> {
            for (String serviceType : expectedServices) {
                Allure.step("Проверка плейсхолдеров для: " + serviceType, () -> {
                    System.out.println("\n--- Проверка плейсхолдеров для: " + serviceType + " ---");

                    if (serviceTypes.contains(serviceType)) {
                        List<String> placeholders = topUpSection.getFieldPlaceholdersForServiceType(serviceType);
                        System.out.println("Плейсхолдеры для '" + serviceType + "':");


                        Allure.addAttachment("Плейсхолдеры для " + serviceType, "text/plain",
                                placeholders.isEmpty() ? "Плейсхолдеры не найдены" : String.join("\n", placeholders), "txt");

                        assertFalse(placeholders.isEmpty(),
                                "Должны быть найдены плейсхолдеры для " + serviceType);

                        for (String placeholder : placeholders) {
                            System.out.println("  - " + placeholder);
                        }

                        boolean hasAmountField = placeholders.stream()
                                .anyMatch(p -> p.contains("Сумма") || p.contains("сумма"));
                        assertTrue(hasAmountField,
                                "Должно быть поле для суммы в " + serviceType);

                        boolean hasEmailField = placeholders.stream()
                                .anyMatch(p -> p.contains("E-mail") || p.contains("email"));
                        assertTrue(hasEmailField,
                                "Должно быть поле для email в " + serviceType);


                        Allure.addAttachment("Результат проверки " + serviceType, "text/plain",
                                String.format("Поле суммы найдено: %s\nПоле email найдено: %s",
                                        hasAmountField, hasEmailField), "txt");

                    } else {
                        System.out.println(" Тип услуги '" + serviceType + "' не найден в списке");
                        Allure.addAttachment("Предупреждение", "text/plain",
                                "Тип услуги '" + serviceType + "' не найден в списке", "txt");
                    }
                });
            }
        });
    }

    @Test
    @Feature("Оплата услуг")
    @Story("Полный поток оплаты для услуги 'Услуги связи'")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяется успешное заполнение формы, переход на страницу оплаты и отображение всех элементов")
    @Issue("MTS-002")
    @TmsLink("TC-002")
    public void testMobileServicePaymentFlow() {
        Allure.step("Начало теста полного потока оплаты", () -> {
            System.out.println("=== Тест полного потока оплаты для 'Услуги связи' ===");
        });

        mainPage.open();
        TopUpSection topUpSection = mainPage.getTopUpSection();

        Allure.step("Выбор услуги связи", () -> {
            System.out.println("\n--- Выбор услуги связи ---");
            topUpSection.selectServiceType("Услуги связи");
        });

        List<String> placeholders = Allure.step("Получение плейсхолдеров полей формы", () -> {
            List<String> ph = topUpSection.getAllPlaceholderTexts();
            System.out.println("Плейсхолдеры полей:");
            ph.forEach(p -> System.out.println("  - " + p));
            return ph;
        });

        Allure.addAttachment("Плейсхолдеры полей формы", "text/plain",
                String.join("\n", placeholders), "txt");

        Allure.step("Заполнение формы тестовыми данными", () -> {
            System.out.println("\n--- Заполнение формы ---");
            topUpSection.fillPhoneNumber("297777777");
            topUpSection.fillAmount("10");

            Allure.addAttachment("Тестовые данные", "text/plain",
                    "Номер телефона: 297777777\nСумма: 10 BYN", "txt");
        });

        Allure.step("Проверка активности кнопки 'Продолжить'", () -> {
            boolean isEnabled = topUpSection.isContinueButtonEnabled();
            assertTrue(isEnabled, "Кнопка 'Продолжить' должна быть активной после заполнения");

            Allure.addAttachment("Статус кнопки", "text/plain",
                    "Кнопка 'Продолжить' активна: " + isEnabled, "txt");
        });

        Allure.step("Переход на страницу оплаты", () -> {
            topUpSection.clickContinueButton();
        });

        PaymentPage paymentPage = new PaymentPage(driver);

        Allure.step("Проверка загрузки страницы оплаты", () -> {
            System.out.println("\n--- Проверка страницы оплаты ---");
            boolean isDisplayed = paymentPage.isPaymentPageDisplayed();
            assertTrue(isDisplayed, "Страница оплаты должна быть отображена");

            Allure.addAttachment("Статус страницы оплаты", "text/plain",
                    "Страница оплаты загружена: " + isDisplayed, "txt");
        });

        Allure.step("Проверка отображения введенных данных", () -> {
            String displayedAmount = paymentPage.getDisplayedAmount();
            System.out.println("Отображаемая сумма: " + displayedAmount);

            String displayedPhone = paymentPage.getDisplayedPhoneNumber();
            System.out.println("Отображаемый номер: " + displayedPhone);

            String amountOnButton = paymentPage.getAmountOnButton();
            System.out.println("Текст кнопки: " + amountOnButton);


            Allure.addAttachment("Информация о платежной странице", "text/plain",
                    String.format("Отображаемая сумма: %s\nОтображаемый телефон: %s\nТекст кнопки: %s",
                            displayedAmount, displayedPhone, amountOnButton), "txt");


            boolean hasValidData = !displayedAmount.equals("Сумма не найдена") ||
                    !displayedPhone.equals("Номер телефона не найден") ||
                    !amountOnButton.equals("Кнопка оплаты не найдена");

            assertTrue(hasValidData,
                    "Должна быть найдена хотя бы одна из проверяемых частей: сумма, номер телефона или кнопка оплаты");


            if (!displayedAmount.equals("Сумма не найдена") && !displayedAmount.startsWith("Ошибка")) {
                System.out.println("✓ Сумма найдена: " + displayedAmount);
            } else {
                System.out.println("⚠ Сумма не найдена или произошла ошибка");
            }

            if (!displayedPhone.equals("Номер телефона не найден") && !displayedPhone.startsWith("Ошибка")) {
                System.out.println("✓ Номер телефона найден: " + displayedPhone);
            } else {
                System.out.println("⚠ Номер телефона не найден или произошла ошибка");
            }

            if (!amountOnButton.equals("Кнопка оплаты не найдена") && !amountOnButton.startsWith("Ошибка")) {
                System.out.println("✓ Кнопка оплаты найдена: " + amountOnButton);
            } else {
                System.out.println("⚠ Кнопка оплаты не найдена или произошла ошибка");
            }
        });

        Allure.step("Проверка полей для ввода данных карты", () -> {
            System.out.println("\n--- Проверка полей карты ---");
            List<String> cardPlaceholders = paymentPage.getCardFieldPlaceholders();
            System.out.println("Плейсхолдеры полей карты:");
            cardPlaceholders.forEach(p -> System.out.println("  - " + p));

            Allure.addAttachment("Поля для ввода данных карты", "text/plain",
                    String.join("\n", cardPlaceholders), "txt");

            assertFalse(cardPlaceholders.isEmpty(),
                    "Должны быть найдены поля для ввода реквизитов карты");
        });

        Allure.step("Проверка иконок платежных систем", () -> {
            System.out.println("\n--- Проверка иконок платежных систем ---");
            boolean hasIcons = paymentPage.hasPaymentSystemIcons();
            System.out.println("Есть иконки платежных систем: " + hasIcons);

            if (hasIcons) {
                List<String> paymentIcons = paymentPage.getPaymentSystemIcons();
                System.out.println("Найденные иконки:");
                paymentIcons.forEach(icon -> System.out.println("  - " + icon));

                Allure.addAttachment("Иконки платежных систем", "text/plain",
                        paymentIcons.isEmpty() ? "Иконки не найдены" : String.join("\n", paymentIcons), "txt");

                System.out.println("✓ Проверка иконок завершена (найдено: " + paymentIcons.size() + ")");
            } else {
                System.out.println("⚠ Иконки платежных систем не найдены");
                Allure.addAttachment("Предупреждение об иконках", "text/plain",
                        "Иконки платежных систем не найдены", "txt");
            }
        });
    }

    @Test
    @Feature("UI блоки")
    @Story("Проверка заголовка блока 'Онлайн пополнение'")
    @Severity(SeverityLevel.MINOR)
    @Description("Проверяется, что заголовок блока соответствует ожидаемому")
    @Issue("MTS-003")
    @TmsLink("TC-003")
    public void testTopUpBlockTitle(){
        Allure.step("Проверка заголовка блока пополнения", () -> {
            mainPage.open();

            String actualTitle = mainPage.getTopUpTitle();
            String expectedTitle = "Онлайн пополнение без комиссии";

            Allure.addAttachment("Заголовки", "text/plain",
                    String.format("Ожидаемый заголовок: %s\nФактический заголовок: %s",
                            expectedTitle, actualTitle), "txt");

            System.out.println("Найденный заголовок: [" + actualTitle + "]");
            assertEquals(expectedTitle, actualTitle, "Заголовок блока не совпадает с ожидаемым");
        });
    }

    @Test
    @Feature("UI блоки")
    @Story("Проверка количества и названий логотипов платежных систем")
    @Severity(SeverityLevel.MINOR)
    @Description("Проверяется, что отображается 5 логотипов, и выводятся их alt-тексты")
    @Issue("MTS-004")
    @TmsLink("TC-004")
    public void testPaymentLogosCount(){
        Allure.step("Проверка логотипов платежных систем", () -> {
            mainPage.open();

            int logosCount = mainPage.getPaymentLogosCount();
            assertEquals(5, logosCount, "Ожидалось 5 логотипов, но найдено: " + logosCount);

            List<String> altTexts = mainPage.getPaymentLogosAltTexts();
            StringBuilder logosInfo = new StringBuilder();
            logosInfo.append("Количество логотипов: ").append(logosCount).append("\n\n");

            for (int i = 0; i < altTexts.size(); i++) {
                String altText = altTexts.get(i);
                System.out.println("Логотип " + (i + 1) + ": " + altText);
                logosInfo.append("Логотип ").append(i + 1).append(": ").append(altText).append("\n");
            }

            Allure.addAttachment("Логотипы платежных систем", "text/plain",
                    logosInfo.toString(), "txt");
        });
    }

    @Test
    @Feature("UI блоки")
    @Story("Проверка ссылки 'Подробнее о сервисе'")
    @Severity(SeverityLevel.MINOR)
    @Description("Проверяется переход по ссылке 'Подробнее о сервисе' на другую страницу")
    @Issue("MTS-005")
    @TmsLink("TC-005")
    public void testMoreAboutServiceLink(){
        Allure.step("Проверка ссылки 'Подробнее о сервисе'", () -> {
            mainPage.open();

            String initialUrl = driver.getCurrentUrl();
            System.out.println("Исходная страница: " + initialUrl);

            mainPage.clickMoreAboutServiceLink();

            String newUrl = driver.getCurrentUrl();
            System.out.println("Переход выполнен, новая страница: " + newUrl);

            Allure.addAttachment("URL переходов", "text/plain",
                    String.format("Исходная страница: %s\nНовая страница: %s\nПереход выполнен: %s",
                            initialUrl, newUrl, !initialUrl.equals(newUrl)), "txt");

            assertNotEquals(initialUrl, newUrl, "Переход по ссылке не выполнен");
        });
    }

    @Test
    public void testWithManualScreenshots() {
        Allure.step("Открытие главной страницы", () -> {
            mainPage.open();


            Allure.addAttachment("Главная страница загружена", "image/png",
                    new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)), "png");
        });

        Allure.step("Заполнение формы", () -> {
            TopUpSection topUpSection = mainPage.getTopUpSection();
            topUpSection.fillPhoneNumber("297777777");


            Allure.addAttachment("Форма заполнена", "image/png",
                    new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)), "png");
        });
    }

    @Test
    public void testWithStepScreenshots() {
        Allure.step("Шаг 1: Открытие страницы", () -> {
            mainPage.open();
            takeScreenshot("Страница открыта");
        });

        Allure.step("Шаг 2: Выбор услуги", () -> {
            TopUpSection topUpSection = mainPage.getTopUpSection();
            topUpSection.selectServiceType("Услуги связи");
            takeScreenshot("Услуга выбрана");
        });
    }

    private void takeScreenshot(String description) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(description, "image/png",
                    new ByteArrayInputStream(screenshot), "png");
        } catch (Exception e) {
            System.out.println("Ошибка скриншота: " + e.getMessage());
        }
    }
}
