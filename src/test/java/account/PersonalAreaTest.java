package account;

import api.client.UserApiClient;
import api.model.user.CreateUserResponse;
import api.model.user.LoginUserRequest;
import api.model.user.LoginUserResponse;
import api.model.user.User;
import core.BaseTest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.LoginPage;
import page.MainPage;
import page.ProfilePage;

import java.time.Duration;

import static api.helper.UserGenerator.getRandomUser;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.*;

public class PersonalAreaTest extends BaseTest {
    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final ProfilePage profilePage = new ProfilePage();
    private final User user = getRandomUser();
    private final String email = user.getEmail();
    private final String password = user.getPassword();
    private final UserApiClient userApiClient = new UserApiClient();

    @Test
    @DisplayName("Проверь переход по клику на «Личный кабинет»")
    public void toMainPageFromAccountEnterOnPersonalAccount() {
        createUser();
        mainPage.clickPersonalArea();
        auth(email, password);
        mainPage.clickPersonalArea();
        assertTrue(profilePage.profileLinkVisible());
        deleteAccount(email, password);
    }

    @Test
    @DisplayName("Проверь переход из личного кабинета в конструктор по клику на логотип Stellar Burgers")
    public void toMainPageFromAccountPageWithLogoButton() {
        createUser();
        mainPage.clickPersonalArea();
        auth(email, password);
        assertTrue(mainPage.createOrderButtonVisible());
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='Конструктор']")));
        profilePage.clickLogoButton();
        mainPage.createOrderButtonVisible();
        deleteAccount(email, password);
    }

    @Test
    @DisplayName("Проверь переход из личного кабинета в конструктор по клику на «Конструктор»")
    public void toMainPageFromAccountPageWithConstructorButton() {
        createUser();
        mainPage.clickPersonalArea();
        auth(email, password);
        assertTrue(mainPage.createOrderButtonVisible());
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='Конструктор']")));
        profilePage.clickConstructorButton();
        mainPage.createOrderButtonVisible();
        deleteAccount(email, password);
    }

    @Test
    @DisplayName("Проверь выход по кнопке «Выйти» в личном кабинете")
    public void toMainPageFromAccountPageExit() {
        createUser();
        mainPage.clickPersonalArea();
        auth(email, password);
        mainPage.clickPersonalArea();
        profilePage.clickExitButton();
        assertTrue(loginPage.enterTitleVisible());
        deleteAccount(email, password);
    }


    @Step("Создание аккаунта")
    public void createUser() {
        Response createResponse = userApiClient.createUser(user);
        assertEquals(SC_OK, createResponse.statusCode());
        CreateUserResponse createUserResponse = createResponse.as(CreateUserResponse.class);
        assertTrue(createUserResponse.getSuccess());
    }

    @Step("Удалить аккаунт")
    private void deleteAccount(String email, String password) {
        LoginUserRequest loginUserRequest = new LoginUserRequest(email, password);
        Response loginResponse = userApiClient.loginUser(loginUserRequest);
        assertEquals(SC_OK, loginResponse.statusCode());

        LoginUserResponse loginUserResponse = loginResponse.as(LoginUserResponse.class);
        String token = loginUserResponse.getAccessToken();

        Response deleteResponse = userApiClient.deleteUser(token);
        assertEquals(SC_ACCEPTED, deleteResponse.statusCode());
    }

    @Step("Вход в личный кабинет")
    private void auth(String email, String password) {
        loginPage.clickEnterTitle();
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickEnterButton();
    }
}
