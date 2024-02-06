package api.client;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static api.config.ConfigApp.BASE_URL_API;
import static io.restassured.RestAssured.given;

public class BaseApiClient {
    public RequestSpecification getPostSpec() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL_API);
    }
}
