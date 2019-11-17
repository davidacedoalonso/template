package demo.cucumber;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class WebRequestUtils {
    public static Response postCall(Headers headers, String url, String payload) {
        return RestAssured.given()
                .log().all()
                .relaxedHTTPSValidation()
                .headers(headers)
                .body(payload)
                .when().post(url)
                .then()
                .log().all()
                .extract()
                .response();
    }
}
