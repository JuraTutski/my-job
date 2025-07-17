package org.example;



import org.example.pages.MainPage;
import org.example.pages.PaymentPage;
import org.example.pages.TopUpSection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MtsTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;

    @BeforeEach
    protected void setUp(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        mainPage = new MainPage(driver);
    }

    @AfterEach
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }

    @Test
    public void testAllServiceTypesPlaceholders() {
        System.out.println("=== Тест плейсхолдеров для всех типов услуг ===");

        mainPage.open();
        TopUpSection topUpSection = mainPage.getTopUpSection();


        List<String> serviceTypes = topUpSection.getAvailableServiceTypes();
        System.out.println("Доступные типы услуг: " + serviceTypes);


        String[] expectedServices = {"Услуги связи", "Домашний интернет", "Рассрочка", "Задолженность"};

        for (String serviceType : expectedServices) {
            System.out.println("\n--- Проверка плейсхолдеров для: " + serviceType + " ---");

            if (serviceTypes.contains(serviceType)) {
                List<String> placeholders = topUpSection.getFieldPlaceholdersForServiceType(serviceType);
                System.out.println("Плейсхолдеры для '" + serviceType + "':");

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

            } else {
                System.out.println(" Тип услуги '" + serviceType + "' не найден в списке");
            }
        }
    }

    @Test
    public void testMobileServicePaymentFlow() {
        System.out.println("=== Тест полного потока оплаты для 'Услуги связи' ===");

        mainPage.open();
        TopUpSection topUpSection = mainPage.getTopUpSection();


        System.out.println("\n--- Выбор услуги связи ---");
        topUpSection.selectServiceType("Услуги связи");


        List<String> placeholders = topUpSection.getAllPlaceholderTexts();
        System.out.println("Плейсхолдеры полей:");
        placeholders.forEach(p -> System.out.println("  - " + p));


        System.out.println("\n--- Заполнение формы ---");
        topUpSection.fillPhoneNumber("297777777");
        topUpSection.fillAmount("10");


        assertTrue(topUpSection.isContinueButtonEnabled(),
                "Кнопка 'Продолжить' должна быть активной после заполнения");


        topUpSection.clickContinueButton();


        System.out.println("\n--- Проверка страницы оплаты ---");
        PaymentPage paymentPage = new PaymentPage(driver);

        assertTrue(paymentPage.isPaymentPageDisplayed(),
                "Страница оплаты должна быть отображена");


        String displayedAmount = paymentPage.getDisplayedAmount();
        System.out.println("Отображаемая сумма: " + displayedAmount);
        assertFalse(displayedAmount.equals("Сумма не найдена"),
                "Сумма должна быть отображена");


        String displayedPhone = paymentPage.getDisplayedPhoneNumber();
        System.out.println("Отображаемый номер: " + displayedPhone);
        assertFalse(displayedPhone.equals("Номер телефона не найден"),
                "Номер телефона должен быть отображен");


        String amountOnButton = paymentPage.getAmountOnButton();
        System.out.println("Текст кнопки: " + amountOnButton);
        assertFalse(amountOnButton.equals("Кнопка оплаты не найдена"),
                "Кнопка оплаты должна быть найдена");


        System.out.println("\n--- Проверка полей карты ---");
        List<String> cardPlaceholders = paymentPage.getCardFieldPlaceholders();
        System.out.println("Плейсхолдеры полей карты:");
        cardPlaceholders.forEach(p -> System.out.println("  - " + p));

        assertFalse(cardPlaceholders.isEmpty(),
                "Должны быть найдены поля для ввода реквизитов карты");


        System.out.println("\n--- Проверка иконок платежных систем ---");
        boolean hasIcons = paymentPage.hasPaymentSystemIcons();
        System.out.println("Есть иконки платежных систем: " + hasIcons);
        assertTrue(hasIcons, "Должны быть иконки платежных систем");

        if (hasIcons) {
            List<String> paymentIcons = paymentPage.getPaymentSystemIcons();
            System.out.println("Найденные иконки:");
            paymentIcons.forEach(icon -> System.out.println("  - " + icon));

            assertFalse(paymentIcons.isEmpty(),
                    "Должны быть найдены конкретные иконки платежных систем");
        }


    }

    @Test
    public void testTopUpBlockTitle(){
        mainPage.open();

        String actualTitle = mainPage.getTopUpTitle();
        String expectedTitle = "Онлайн пополнение без комиссии";

        System.out.println("Найденный заголовок: [" + actualTitle + "]");
        assertEquals(expectedTitle, actualTitle, "Заголовок блока не совпадает с ожидаемым");
    }

    @Test
    public void testPaymentLogosCount(){
        mainPage.open();

        int logosCount = mainPage.getPaymentLogosCount();
        assertEquals(5, logosCount, "Ожидалось 5 логотипов, но найдено: " + logosCount);

        List<String> altTexts = mainPage.getPaymentLogosAltTexts();
        for (String altText : altTexts) {
            System.out.println("Логотип: " + altText);
        }
    }

    @Test
    public void testMoreAboutServiceLink(){
        mainPage.open();

        String initialUrl = driver.getCurrentUrl();
        mainPage.clickMoreAboutServiceLink();

        String newUrl = driver.getCurrentUrl();
        System.out.println("Переход выполнен, новая страница: " + newUrl);
        assertNotEquals(initialUrl, newUrl, "Переход по ссылке не выполнен");
    }
}
