package util;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class InitializeSpecification {

    public static RequestSpecification createRequestSpecification() {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com/")
                .addHeader("Content-Type", "application/json")
                .build();

        return requestSpecification;
    }

    public static ResponseSpecification createResponseSpecification() {
        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        return responseSpecification;
    }
}
