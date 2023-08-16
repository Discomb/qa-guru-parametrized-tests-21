package guru.qa;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@DisplayName("Параметризованные тесты")
public class VprokTests extends BaseTest {

    @ValueSource(
            strings = {"Хлеб", "Шоколад", "Молоко"}
    )
    @DisplayName("Проверка соответствия строки результата поиска запросу")
    @ParameterizedTest(name = "Запрос \"{0}\"")
    public void searchResultMatchesTheQueryTest(String searchQuery) {
        open(baseUrl);

        $("input[data-test-search-input='true']").setValue(searchQuery);
        $("input[data-test-search-input='true']").submit();

        $("div[class^='SearchResultsInformer']").shouldHave(Condition.matchText(String.format("(?:По запросу \"%s\" (найдено|найден) )\\d{1,}(?: (товара|товаров|товар))", searchQuery)));

    }

    @EnumSource(CatalogEntries.class)
    @DisplayName("Проверка отображения разделов каталога")
    @ParameterizedTest(name = "Раздел \"{0}\"")
    public void catalogShouldHaveEntryTest(CatalogEntries catalogEntry) {

        open(baseUrl);

        $("button[aria-label='Header Burger']").$(byText("Каталог")).click();

        $("div[class^='CatalogMenu_parents']").shouldHave(Condition.text(catalogEntry.description));

    }

    @CsvSource(delimiter = '|', textBlock = """
            улица Новаторов, 6          | 54
            Ломоносовский проспект, 23  | 135
            Высокая улица, 5к2          | 85
            """)
    @DisplayName("Проверка отображения введенного адреса на кнопке")
    @ParameterizedTest(name = "Адрес: {0}, кв. {1}")
    public void addressButtonShouldShowBeginningOfAddressTest(String address, String flat) {
        open(baseUrl);

        $("div[class*='LocationTile']").click();
        $("input[name='address']").setValue(address);
        $("ul[class*='Options_list']").$$("li").findBy(Condition.text(address)).click();
        $("input[name='flat']").setValue(flat);
        $(byText("Сохранить адрес")).click();

        $("div[class*='LocationTile']").shouldHave(Condition.text(address.substring(0, 10)));

    }
}
