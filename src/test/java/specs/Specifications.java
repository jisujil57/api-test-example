package specs;

import io.restassured.RestAssured;
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
        public static RequestSpecification requestSpec = with()
                .filter(withCustomTemplates())
                .log().uri()
                .log().method()
                .log().body()
                .log().headers()
                .contentType(JSON)
                .baseUri(System.getProperty("baseUrl", "https://reqres.in"))
                .basePath("/api");

        public static ResponseSpecification responseSpecOk200 = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectResponseTime(lessThan(2000L))
                .log(STATUS)
                .log(BODY)
                .build();

        public static ResponseSpecification responseSpecNoContent204 = new ResponseSpecBuilder()
                .expectStatusCode(204)
                .expectResponseTime(lessThan(2000L))
                .log(STATUS)
                .log(BODY)
                .build();
}
