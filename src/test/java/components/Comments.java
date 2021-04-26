package components;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import util.InitializeSpecification;

import static io.restassured.RestAssured.given;

public class Comments {
    private static RequestSpecification requestSpecification;
    private static ResponseSpecification responseSpecification;

    public static Response getCommentsByPostId(int postId) {
        requestSpecification = InitializeSpecification.createRequestSpecification();
        responseSpecification = InitializeSpecification.createResponseSpecification();

        Response commentsOnUserPostResponse = given()
                .spec(requestSpecification)
                .param("postId", postId)
                .get("comments");

        commentsOnUserPostResponse.then()
                .assertThat()
                .spec(responseSpecification);

        return commentsOnUserPostResponse;
    }
}
