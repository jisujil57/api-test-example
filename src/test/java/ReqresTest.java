import modeles.RegisterRequestBody;
import modeles.RegisterResponseBody;
import modeles.UnknownDataResponse;
import modeles.UserDataResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static specs.Specifications.*;

public class ReqresTest {

    @Test
    @DisplayName("Проверка получения списка пользователей")
    void getUsersTest2() {
        List<UserDataResponse> userDataResponses = given(requestSpec)
                .queryParam("page", 2)
                .when()
                .get(Endpoints.USERS_ENDPOINT)
                .then()
                .spec(responseSpecOk200)
                .assertThat()
                .extract().jsonPath().getList("data", UserDataResponse.class);

        assertFalse(userDataResponses.isEmpty());
        assertEquals(6, userDataResponses.size());

        for (UserDataResponse userData : userDataResponses) {
            assertTrue(userData.getId() > 0);
            assertThat(userData.getEmail(), endsWith("@reqres.in"));
            assertNotNull(userData.getFirst_name());
            assertNotNull(userData.getLast_name());
            assertThat(userData.getAvatar(), startsWith("https://reqres.in/img/faces/"));

        }
    }

    @Test
    @DisplayName("Регистрация нового пользователя")
    void registerUserTest() {
        RegisterRequestBody registerUserRequestBody = RegisterRequestBody.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        RegisterResponseBody registerResponseBody = given(requestSpec)
                .body(registerUserRequestBody)
                .when()
                .post(Endpoints.REGISTER_ENDPOINT)
                .then()
                .spec(responseSpecOk200)
                .extract().body().as(RegisterResponseBody.class);

        assertEquals(4, registerResponseBody.getId());
        assertNotNull(registerResponseBody.getToken());
    }

    @Test
    @DisplayName("Получение информации о пользователе с ID 2")
    void getUserInfoTest() {
        UserDataResponse userDataResponse = given(requestSpec)
                .when()
                .get(Endpoints.USER_INFO_ENDPOINT)
                .then()
                .spec(responseSpecOk200)
                .extract().jsonPath().getObject("data", UserDataResponse.class);

        assertEquals(2, userDataResponse.getId());
        assertEquals("janet.weaver@reqres.in", userDataResponse.getEmail());
        assertEquals("Janet", userDataResponse.getFirst_name());
        assertEquals("Weaver", userDataResponse.getLast_name());
        assertEquals("https://reqres.in/img/faces/2-image.jpg", userDataResponse.getAvatar());
    }

    @Test
    @DisplayName("Удаление пользователя с ID 2")
    void deleteUserTest() {
        given(requestSpec)
                .when()
                .delete(Endpoints.USER_INFO_ENDPOINT)
                .then()
                .spec(responseSpecNoContent204);
    }

    @Test
    @DisplayName("Получение информации о неизвестных объектах")
    void getUnknownObjectsTest() {
        List<UnknownDataResponse> unknownDataResponse = given(requestSpec)
                .when()
                .get(Endpoints.UNKNOWN_ENDPOINT)
                .then()
                .spec(responseSpecOk200)
                .extract().jsonPath().getList("data", UnknownDataResponse.class);

        assertFalse(unknownDataResponse.isEmpty());
        assertEquals(6, unknownDataResponse.size());

        for (UnknownDataResponse unknownData : unknownDataResponse) {
            assertTrue(unknownData.getId() > 0);
            assertNotNull(unknownData.getName());
            assertTrue(unknownData.getYear() > 1995);
            assertNotNull(unknownData.getColor());
            assertNotNull(unknownData.getPantone_value());
        }
    }
}
