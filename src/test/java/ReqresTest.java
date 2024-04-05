import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqresTest {
    @Test
    @DisplayName("Проверка получения списка пользователей")
    void getUsersTest() {
        String getUsersRequestBody = "{ \"pageNumber\": \"2\" }";

        given()
                .log().uri()
                .log().method()
                .baseUri(Endpoints.BASE_URL)
                .accept(ContentType.JSON)
                .body(getUsersRequestBody)
                .when()
                .get(Endpoints.USERS_ENDPOINT)
                .then()
                .log().all()
                .statusCode(200)
                .time(lessThan(600L))
                .body("total", is(12))
                .body("data.email", hasItems(endsWith("@reqres.in")))
                .body("data.avatar", hasItems(startsWith("https://reqres.in/img/faces/")))
                .body("data", hasSize(6))
                .body("data", notNullValue());
    }

    @Test
    @DisplayName("Регистрация нового пользователя")
    void registerUserTest() {
        String registerUserRequestBody = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .log().uri()
                .log().method()
                .baseUri(Endpoints.BASE_URL)
                .contentType(ContentType.JSON)
                .body(registerUserRequestBody)
                .when()
                .post(Endpoints.REGISTER_ENDPOINT)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Получение информации о пользователе с ID 2")
    void getUserInfoTest() {
        given()
                .log().uri()
                .log().method()
                .baseUri(Endpoints.BASE_URL)
                .when()
                .get(Endpoints.USER_INFO_ENDPOINT)
                .then()
                .log().all()
                .statusCode(200)
                .body("data.email", is("janet.weaver@reqres.in"))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"))
                .body("data.id", notNullValue());
    }

    @Test
    @DisplayName("Удаление пользователя с ID 2")
    void deleteUserTest() {
        given()
                .log().uri()
                .log().method()
                .baseUri(Endpoints.BASE_URL)
                .when()
                .delete(Endpoints.USER_INFO_ENDPOINT)
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    @DisplayName("Получение информации о неизвестных объектах")
    void getUnknownObjectsTest() {
        given()
                .log().uri()
                .log().method()
                .baseUri(Endpoints.BASE_URL)
                .when()
                .get(Endpoints.UNKNOWN_ENDPOINT)
                .then()
                .log().all()
                .statusCode(200)
                .body("page", is(1))
                .body("per_page", is(6))
                .body("total", is(12))
                .body("total_pages", is(2))
                .body("data", hasSize(6))
                .body("support.url", equalTo("https://reqres.in/#support-heading"))
                .body("support.text",
                        equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }
}
