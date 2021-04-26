package components;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import util.InitializeSpecification;

import static io.restassured.RestAssured.given;

public class Users {
    private static RequestSpecification requestSpecification;
    private static ResponseSpecification responseSpecification;

    public static Response getUser(String username) {
        requestSpecification = InitializeSpecification.createRequestSpecification();
        responseSpecification = InitializeSpecification.createResponseSpecification();

        Response userResponse = given()
                .spec(requestSpecification)
                .param("username", username)
                .get("users");

        userResponse.then()
                .assertThat()
                .spec(responseSpecification);

        return userResponse;
    }

    public static Response getAllUsers() {
        requestSpecification = InitializeSpecification.createRequestSpecification();
        responseSpecification = InitializeSpecification.createResponseSpecification();

        Response allUsersResponse = given()
                .spec(requestSpecification)
                .get("users");

        allUsersResponse.then()
                .assertThat()
                .spec(responseSpecification);

        return allUsersResponse;
    }
}
