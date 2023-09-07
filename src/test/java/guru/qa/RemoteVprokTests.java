package guru.qa;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

@Tag("remote")
@DisplayName("Параметризованные тесты")
public class RemoteVprokTests extends BaseTest {

    @ValueSource(
            strings = {"Хлеб", "Шоколад", "Молоко"}
    )
    @DisplayName("Проверка соответствия строки результата поиска запросу")
    @Description("Проверка соответствия строки результата поиска запросу")
    @ParameterizedTest(name = "Запрос \"{0}\"")
    public void searchResultMatchesTheQueryTest(String searchQuery) {
        step("Открываем главную страницу", () -> {
            open(baseUrl);
        });
        step("Выполняем поиск товара " + searchQuery, () -> {
            $("input[data-test-search-input='true']").setValue(searchQuery);
            $("input[data-test-search-input='true']").submit();
        });
        step("Проверяем сообщение в результате поиска", () -> {
            $("div[class^='SearchResultsInformer']").shouldHave(Condition.matchText(String.format("(?:По запросу \"%s\" (найдено|найден) )\\d{1,}(?: (товара|товаров|товар))", searchQuery)));
        });
    }

    @MethodSource("guru.qa.TestData#testDataFactory")
    @DisplayName("Проверка соответствия строки результата поиска запросу (Method Source)")
    @Description("Проверка соответствия строки результата поиска запросу (Method Source)")
    @ParameterizedTest(name = "Запрос \"{0}\"")
    public void searchResultMatchesTheQueryMSTest(String searchQuery) {
        step("Открываем главную страницу", () -> {
            open(baseUrl);
        });
        step("Выполняем поиск товара " + searchQuery, () -> {
            $("input[data-test-search-input='true']").setValue(searchQuery);
            $("input[data-test-search-input='true']").submit();
        });
        step("Проверяем сообщение в результате поиска", () -> {
            $("div[class^='SearchResultsInformer']").shouldHave(Condition.matchText(String.format("(?:По запросу \"%s\" (найдено|найден) )\\d{1,}(?: (товара|товаров|товар))", searchQuery)));
        });

    }

    @EnumSource(CatalogEntries.class)
    @DisplayName("Проверка отображения разделов каталога")
    @Description("Проверка отображения разделов каталога")
    @ParameterizedTest(name = "Раздел \"{0}\"")
    public void catalogShouldHaveEntryTest(CatalogEntries catalogEntry) {
        step("Открываем главную страницу", () -> {
            open(baseUrl);
        });
        step("Открываем каталог", () -> {
            $("button[aria-label='Header Burger']").$(byText("Каталог")).click();
        });
        step("Ищем описание раздела: " + catalogEntry.description, () -> {
            $("div[class^='CatalogMenu_parents']").shouldHave(Condition.text(catalogEntry.description));
        });
    }

    @CsvSource(delimiter = '|', textBlock = """
            улица Новаторов, 6          | 54
            Ломоносовский проспект, 23  | 135
            Высокая улица, 5к2          | 85
            """)
    @DisplayName("Проверка отображения введенного адреса на кнопке")
    @Description("Проверка отображения введенного адреса на кнопке")
    @ParameterizedTest(name = "Адрес: {0}, кв. {1}")
    public void addressButtonShouldShowBeginningOfAddressTest(String address, String flat) {
        step("Открываем главную страницу", () -> {
            open(baseUrl);
        });

        step("Указываем адрес доставки: " + address + ", " + flat, () -> {
            $("div[class*='LocationTile']").click();
            $("input[name='address']").setValue(address);
            $("ul[class*='Options_list']").$$("li").findBy(Condition.text(address)).click();
            $("input[name='flat']").setValue(flat);
            $(byText("Сохранить адрес")).click();
        });

        step("Проверяем отображение адреса на главной странице", () ->{
            $("div[class*='LocationTile']").shouldHave(Condition.text(address.substring(0, 10)));
        });
    }
}
