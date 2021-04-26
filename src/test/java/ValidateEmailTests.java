import entities.Comment;
import entities.Post;
import entities.User;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import util.InitializeSpecification;
import java.util.Arrays;
import java.util.List;
import static io.restassured.RestAssured.given;

public class ValidateEmailTests extends InitializeSpecification {
    private int userId;
    private String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

    @DataProvider
    public static Object[][] usernames() {
        return new Object[][] {
                { "Delphine"}
        };
    }

    @Test(dataProvider = "usernames")
    public void verifyUsernameInUsers(String username) {
        Response userResponse = given()
                .spec(requestSpecification)
                .param("username", username)
                .get("users");

        userResponse.then()
                .assertThat()
                .spec(responseSpecification);

        List<User> users = Arrays.asList(userResponse.as(User[].class));

        User user = users.get(0);

        userId = user.getId();

        Assert.assertEquals(user.getUsername(), username);
    }

    @Test(dependsOnMethods = "getUserByUsername")
    public void verifyEmailFormatInPostComments() {
        SoftAssert softAssert = new SoftAssert();
        Response userPostsResponse = given()
                .spec(requestSpecification)
                .param("userId", userId)
                .get("posts");

        userPostsResponse.then()
                .assertThat()
                .spec(responseSpecification);

        List<Post> userPosts = Arrays.asList(userPostsResponse.as(Post[].class));

        userPosts.forEach(userPost -> {
            Response commentsOnUserPostResponse = given()
                    .spec(requestSpecification)
                    .param("postId", userPost.getId())
                    .get("comments");

            commentsOnUserPostResponse.then()
                    .assertThat()
                    .spec(responseSpecification);

            Assert.assertEquals(userPost.getUserId(), userId);

            List<Comment> commentsOnUserPost = Arrays.asList(commentsOnUserPostResponse.as(Comment[].class));

            commentsOnUserPost.forEach(comment -> {
                softAssert.assertTrue(comment.getEmail().matches(emailRegex));
            });
        });

        softAssert.assertAll();
    }
}
