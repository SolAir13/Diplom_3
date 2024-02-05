package main;

import core.BaseTest;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.MainPage;

public class MainTest extends BaseTest {
    private final MainPage mainPage = new MainPage();

    @Test
    @DisplayName("Проверка перехода на вкладку 'Соусы'")
    public void checkClickTabSous() {
        String expectedTab = "Соусы";
        mainPage.personalAreaVisible();
        clickIngredient(expectedTab);
        Assertions.assertEquals(expectedTab, mainPage.getCurrentTabName(), "Вкладка не переключилась на Соусы");
    }
    @Test
    @DisplayName("Проверка перехода на вкладку 'Булки'")
    public void checkClickTabBuns() {
        String expectedTabSous = "Соусы"; //по умолчанию выбран элемент Булки для проверки сначала переключимся на другой элемент;
        String expectedTab = "Булки";
        mainPage.personalAreaVisible();
        clickIngredient(expectedTabSous);
        clickIngredient(expectedTab);
        Assertions.assertEquals(expectedTab, mainPage.getCurrentTabName(), "Вкладка не переключилась на Булки");
    }
    @Test
    @DisplayName("Проверка перехода на вкладку 'Начинки'")
    public void checkClickTabFill() {
        String expectedTab = "Начинки";
        mainPage.personalAreaVisible();
        clickIngredient(expectedTab);
        Assertions.assertEquals(expectedTab, mainPage.getCurrentTabName(), "Вкладка не переключилась на Начинки");
    }

    @Step("Кликнуть на кнопку ингредиента '{ingredient}'")
    public void clickIngredient(String ingredient) {
        mainPage.clickIngredientButton(ingredient);
    }

}
