package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static config.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.lessThan;

public class Specifications {
        private static final long MAX_RESPONSE_TIME = 2000L;
        private static final String BASE_URL = System.getProperty("baseUrl", "https://reqres.in");
        private static final String BASE_PATH = "/api";

        public static RequestSpecification requestSpec = with()
                .filter(withCustomTemplates())
                .log().uri()
                .log().method()
                .log().body()
                .log().headers()
                .contentType(JSON)
                .baseUri(BASE_URL)
                .basePath(BASE_PATH);

        public static ResponseSpecification responseSpecOk200 = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectResponseTime(lessThan(MAX_RESPONSE_TIME))
                .log(STATUS)
                .log(BODY)
                .build();

        public static ResponseSpecification responseSpecNoContent204 = new ResponseSpecBuilder()
                .expectStatusCode(204)
                .expectResponseTime(lessThan(MAX_RESPONSE_TIME))
                .log(STATUS)
                .log(BODY)
                .build();
}
