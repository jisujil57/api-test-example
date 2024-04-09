import io.qameta.allure.Allure;
import modeles.RegisterRequestBody;
import modeles.RegisterResponseBody;
import modeles.UnknownDataResponse;
import modeles.UserDataResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static specs.Specifications.*;

public class ReqresTest {

    @Test
    @DisplayName("Получение списка пользователей")
    void getUsersTest2() {
        List<UserDataResponse> userDataResponses = step("Выполнение запроса на получение списка пользователей", () ->
                given(requestSpec)
                        .queryParam("page", 2)
                        .when()
                        .get(Endpoints.USERS_ENDPOINT)
                        .then()
                        .spec(responseSpecOk200)
                        .assertThat()
                        .extract().jsonPath().getList("data", UserDataResponse.class)
        );

        step("Проверка полученного списка пользователей", () -> {
            assertFalse(userDataResponses.isEmpty());
            assertEquals(6, userDataResponses.size());
        });

        step("Проверка данных каждого пользователя", () -> {
            for (UserDataResponse userData : userDataResponses) {
                step("Проверка ID пользователя", () -> assertTrue(userData.getId() > 0));
                step("Проверка email пользователя", () -> assertThat(userData.getEmail(), endsWith("@reqres.in")));
                step("Проверка имени пользователя", () -> assertNotNull(userData.getFirst_name()));
                step("Проверка фамилии пользователя", () -> assertNotNull(userData.getLast_name()));
                step("Проверка аватара пользователя", () -> assertThat(userData.getAvatar(), startsWith("https://reqres.in/img/faces/")));
            }
        });
    }

    @Test
    @DisplayName("Регистрация нового пользователя")
    void registerUserTest() {
        RegisterRequestBody registerUserRequestBody = RegisterRequestBody.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        RegisterResponseBody registerResponseBody = step("Выполнение запроса на регистрацию нового пользователя", () ->
                given(requestSpec)
                        .body(registerUserRequestBody)
                        .when()
                        .post(Endpoints.REGISTER_ENDPOINT)
                        .then()
                        .spec(responseSpecOk200)
                        .extract().body().as(RegisterResponseBody.class)
        );

        step("Проверка успешной регистрации нового пользователя", () -> {
            Allure.description("ID нового пользователя: " + registerResponseBody.getId() + ", Токен нового пользователя: " + registerResponseBody.getToken());
            assertEquals(4, registerResponseBody.getId());
            assertNotNull(registerResponseBody.getToken());
        });
    }

    @Test
    @DisplayName("Получение информации о пользователе с ID 2")
    void getUserInfoTest() {
        UserDataResponse userDataResponse = step("Выполнение запроса на получение информации о пользователе с ID 2", () ->
                given(requestSpec)
                        .when()
                        .get(Endpoints.USER_INFO_ENDPOINT)
                        .then()
                        .spec(responseSpecOk200)
                        .extract().jsonPath().getObject("data", UserDataResponse.class)
        );

        step("Проверка информации о пользователе с ID 2", () -> {
            assertEquals(2, userDataResponse.getId());
            assertEquals("janet.weaver@reqres.in", userDataResponse.getEmail());
            assertEquals("Janet", userDataResponse.getFirst_name());
            assertEquals("Weaver", userDataResponse.getLast_name());
            assertEquals("https://reqres.in/img/faces/2-image.jpg", userDataResponse.getAvatar());
        });
    }

    @Test
    @DisplayName("Удаление пользователя с ID 2")
    void deleteUserTest() {
        step("Удаление пользователя с ID 2", () ->
                given(requestSpec)
                        .when()
                        .delete(Endpoints.USER_INFO_ENDPOINT)
                        .then()
                        .spec(responseSpecNoContent204)
        );
    }

    @Test
    @DisplayName("Получение информации о неизвестных объектах")
    void getUnknownObjectsTest() {
        List<UnknownDataResponse> unknownDataResponse = step("Выполнение запроса на получение информации о неизвестных объектах", () ->
                given(requestSpec)
                        .when()
                        .get(Endpoints.UNKNOWN_ENDPOINT)
                        .then()
                        .spec(responseSpecOk200)
                        .extract().jsonPath().getList("data", UnknownDataResponse.class)
        );

        step("Проверка информации о неизвестных объектах", () -> {
            assertFalse(unknownDataResponse.isEmpty());
            assertEquals(6, unknownDataResponse.size());

            for (UnknownDataResponse unknownData : unknownDataResponse) {
                step("Проверка ID неизвестного объекта", () -> assertTrue(unknownData.getId() > 0));
                step("Проверка имени неизвестного объекта", () -> assertNotNull(unknownData.getName()));
                step("Проверка года неизвестного объекта", () -> assertTrue(unknownData.getYear() > 1995));
                step("Проверка цвета неизвестного объекта", () -> assertNotNull(unknownData.getColor()));
                step("Проверка значения Pantone неизвестного объекта", () -> assertNotNull(unknownData.getPantone_value()));
            }
        });
    }
}
