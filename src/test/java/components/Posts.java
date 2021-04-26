package components;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import util.InitializeSpecification;

import static io.restassured.RestAssured.given;

public class Posts {
    private static RequestSpecification requestSpecification;
    private static ResponseSpecification responseSpecification;

    public static Response getUserPostsById(int userId) {
        requestSpecification = InitializeSpecification.createRequestSpecification();
        responseSpecification = InitializeSpecification.createResponseSpecification();

        Response userPostsResponse = given()
                .spec(requestSpecification)
                .param("userId", userId)
                .get("posts");

        userPostsResponse.then()
                .assertThat()
                .spec(responseSpecification);

        return userPostsResponse;
    }
}
