
package org.example.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LogEntry;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AllureScreenshotExtension implements TestWatcher, BeforeEachCallback, AfterEachCallback {

    private WebDriver driver;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void beforeEach(ExtensionContext context) {

        Object testInstance = context.getRequiredTestInstance();
        try {
            Field driverField = testInstance.getClass().getDeclaredField("driver");
            driverField.setAccessible(true);
            this.driver = (WebDriver) driverField.get(testInstance);
        } catch (Exception e) {
            System.out.println("Не удалось получить WebDriver: " + e.getMessage());
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {

        this.driver = null;
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String testName = context.getDisplayName();
        String timeStamp = LocalDateTime.now().format(TIME_FORMATTER);

        attachScreenshot(" Ошибка в тесте '" + testName + "' в " + timeStamp);
        attachPageSource(" HTML страницы при ошибке");
        attachBrowserLogs(" Логи браузера при ошибке");
        attachTestError(" Детали ошибки", cause);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        String testName = context.getDisplayName();
        String timeStamp = LocalDateTime.now().format(TIME_FORMATTER);

        attachScreenshot(" Успешный тест '" + testName + "' в " + timeStamp);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        String testName = context.getDisplayName();
        String timeStamp = LocalDateTime.now().format(TIME_FORMATTER);

        attachScreenshot(" Прерванный тест '" + testName + "' в " + timeStamp);
    }

    @Attachment(value = "{name}", type = "image/png")
    public byte[] attachScreenshot(String name) {
        if (driver == null) {
            System.out.println("WebDriver не найден для создания скриншота");
            return new byte[0];
        }

        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), "png");
            return screenshot;
        } catch (Exception e) {
            System.out.println("Не удалось сделать скриншот: " + e.getMessage());
            return new byte[0];
        }
    }

    @Attachment(value = "{name}", type = "text/html")
    public String attachPageSource(String name) {
        if (driver == null) {
            return "WebDriver не найден";
        }

        try {
            String pageSource = driver.getPageSource();
            Allure.addAttachment(name, "text/html", pageSource, "html");
            return pageSource;
        } catch (Exception e) {
            System.out.println("Не удалось получить HTML: " + e.getMessage());
            return "Ошибка получения HTML: " + e.getMessage();
        }
    }

    @Attachment(value = "{name}", type = "text/plain")
    public String attachBrowserLogs(String name) {
        if (driver == null) {
            return "WebDriver не найден";
        }

        try {
            StringBuilder logs = new StringBuilder();
            logs.append("=== ИНФОРМАЦИЯ О БРАУЗЕРЕ ===\n");
            logs.append("URL: ").append(driver.getCurrentUrl()).append("\n");
            logs.append("Title: ").append(driver.getTitle()).append("\n");
            logs.append("Window Size: ").append(driver.manage().window().getSize()).append("\n");
            logs.append("Время: ").append(LocalDateTime.now()).append("\n\n");


            try {
                List<LogEntry> browserLogs = (List<LogEntry>) driver.manage().logs().get(LogType.BROWSER);
                if (!browserLogs.isEmpty()) {
                    logs.append("=== ЛОГИ БРАУЗЕРА ===\n");
                    for (LogEntry entry : browserLogs) {
                        logs.append(entry.getTimestamp())
                                .append(" [").append(entry.getLevel()).append("] ")
                                .append(entry.getMessage()).append("\n");
                    }
                }
            } catch (Exception e) {
                logs.append("Логи браузера недоступны: ").append(e.getMessage()).append("\n");
            }

            Allure.addAttachment(name, "text/plain", logs.toString(), "txt");
            return logs.toString();
        } catch (Exception e) {
            System.out.println("Не удалось получить логи: " + e.getMessage());
            return "Ошибка получения логов: " + e.getMessage();
        }
    }

    @Attachment(value = "{name}", type = "text/plain")
    public String attachTestError(String name, Throwable cause) {
        StringBuilder errorInfo = new StringBuilder();
        errorInfo.append("=== ДЕТАЛИ ОШИБКИ ===\n");
        errorInfo.append("Тип исключения: ").append(cause.getClass().getSimpleName()).append("\n");
        errorInfo.append("Сообщение: ").append(cause.getMessage()).append("\n");
        errorInfo.append("Время: ").append(LocalDateTime.now()).append("\n\n");

        errorInfo.append("=== СТЕК ВЫЗОВОВ ===\n");
        for (StackTraceElement element : cause.getStackTrace()) {
            errorInfo.append(element.toString()).append("\n");
        }

        return errorInfo.toString();
    }

    public void takeScreenshot(String stepName) {
        String timeStamp = LocalDateTime.now().format(TIME_FORMATTER);
        attachScreenshot("📸 " + stepName + " в " + timeStamp);
    }


    public void takeElementScreenshot(WebElement element, String elementName) {
        if (driver == null || element == null) {
            System.out.println("WebDriver или элемент не найден для скриншота");
            return;
        }

        try {
            byte[] screenshot = element.getScreenshotAs(OutputType.BYTES);
            String timeStamp = LocalDateTime.now().format(TIME_FORMATTER);
            String name = "Элемент '" + elementName + "' в " + timeStamp;

            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), "png");
        } catch (Exception e) {
            System.out.println("Не удалось сделать скриншот элемента: " + e.getMessage());
        }
    }


    public static void screenshot(String stepName) {
        try {

            Allure.addAttachment("📸 " + stepName, "image/png",
                    new ByteArrayInputStream(new byte[0]), "png");
        } catch (Exception e) {
            System.out.println("Ошибка при создании скриншота: " + e.getMessage());
        }
    }


}
