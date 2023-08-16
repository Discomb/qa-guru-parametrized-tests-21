package guru.qa;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Configuration.baseUrl;

public class BaseTest {

    @BeforeAll
    public static void beforeAll() {
        Configuration.browserSize = "1980x1020";
        Configuration.pageLoadStrategy = "eager";
        baseUrl = "https://vprok.ru";
    }
}
