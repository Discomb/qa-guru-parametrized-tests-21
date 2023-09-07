package guru.qa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.demoqa.helpers.Attach;

import java.util.Map;

import static com.codeborne.selenide.Configuration.baseUrl;

public class RemoteBaseTest {

    @BeforeAll
    static void beforeAll() {

        String selenoidHome = System.getProperty("selenoidHome");
        String selenoidCreds = System.getProperty("selenoidCreds");

        baseUrl = "https://vprok.ru";
        Configuration.browserSize = System.getProperty("browserSize");
        Configuration.pageLoadStrategy = "eager";
        Configuration.browser = System.getProperty("browser");
        Configuration.browserVersion = System.getProperty("browserVersion");

        System.out.println(selenoidCreds);

        Configuration.remote = "https://" + selenoidCreds + "@" + selenoidHome + "/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true
        ));

        Configuration.browserCapabilities = capabilities;
    }

    @BeforeEach
    void beforeEach() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }

}

