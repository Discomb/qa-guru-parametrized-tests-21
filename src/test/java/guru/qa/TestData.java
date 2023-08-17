package guru.qa;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.TestInstance;

import java.util.Locale;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestData {

    static Locale rus = new Locale("ru", "RU");
    static Faker faker = new Faker(rus);

    public static Stream<String> testDataFactory() {

        return Stream.of(
                faker.food().ingredient(),
                faker.food().vegetable(),
                faker.food().fruit()
        );
    }
}
