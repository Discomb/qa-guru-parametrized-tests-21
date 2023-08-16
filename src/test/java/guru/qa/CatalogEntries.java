package guru.qa;

public enum CatalogEntries {
    NEW("Новинки"),
    OUR("Наше производство"),
    MILK("Молоко, сыр, яйца"),
    MEAT("Мясо, птица, колбасы"),
    FISH("Рыба, икра");

    String description;

    CatalogEntries(String description) {
        this.description = description;
    }
}
