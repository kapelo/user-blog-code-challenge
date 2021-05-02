import components.Comments;
import components.Posts;
import components.Users;
import entities.Comment;
import entities.Post;
import entities.User;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.assertj.core.api.SoftAssertions;
import util.RequestHandler;
import util.TestLogger;
import java.util.List;

public class EmailFormatTests {
    private String emailFormatRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
    private static Logger logger = TestLogger.logger;

    @Test
    public void verifyThatInvalidPathReturns404() {
        SoftAssertions softly = new SoftAssertions();
        Response response = RequestHandler.sendInvalidGetRequest("fake");

        logger.info("Validating response...");
        softly.assertThat(response.getStatusCode()).as("Status code").isEqualTo(404);
        softly.assertThat(response.getContentType()).as("Content type").isEqualTo("application/json; charset=utf-8");
        softly.assertAll();
    }

    @DataProvider
    public static Object[][] invalidUsername() {
        return new Object[][] {
                { "fakeusername1" },
                { "fakeusername2" }
        };
    }

    @Test(dataProvider = "invalidUsername")
    public void verifyInvalidUsernameReturnsAnEmptyArray(String username) {
        SoftAssertions softly = new SoftAssertions();
        List<User> users = Users.getUserDetailsByUsername(username);

        logger.info("Validating response...");
        softly.assertThat(users.size()).as("User with invalid username count").isEqualTo(0);
        softly.assertAll();
    }

    @DataProvider
    public static Object[][] validUsername() {
        return new Object[][] {
                { "Delphine" }
        };
    }

    @Test(dataProvider = "validUsername")
    public void verifyUsersUsername(String username) {
        SoftAssertions softly = new SoftAssertions();
        List<User> users = Users.getUserDetailsByUsername(username);
        User user = users.get(0);

        logger.info("Validating response...");
        softly.assertThat(user.getUsername()).as("Username").isEqualTo(username);
        softly.assertThat(user.getId()).as("User id").isNotNull();
        softly.assertThat(user.getName()).as("User's name").isNotNull();
        softly.assertThat(user.getEmail()).as("User email").isNotNull();
        softly.assertAll();
    }

    @Test
    public void verifyAllUsersHaveUsername() {
        SoftAssertions softly = new SoftAssertions();
        List<User> users = Users.getAllUsers();

        users.forEach(user -> {
            softly.assertThat(user.getUsername()).as("Username").isNotNull();
            softly.assertThat(user.getUsername()).as("Username").isNotEmpty();
        });
        softly.assertAll();
    }

    @Test
    public void verifyAllUsersHaveId() {
        SoftAssertions softly = new SoftAssertions();
        List<User> users = Users.getAllUsers();

        users.forEach(user -> {
            softly.assertThat(user.getId()).as("User id").isNotNull();
        });
        softly.assertAll();
    }

    @Test
    public void verifyThereAreMoreThanZeroUsers() {
        SoftAssertions softly = new SoftAssertions();
        List<User> users = Users.getAllUsers();

        softly.assertThat(users.size()).as("All blog users count").isGreaterThan(0);
        softly.assertAll();
    }

    @Test(dataProvider = "validUsername")
    public void verifyPostsByUser(String username) {
        SoftAssertions softly = new SoftAssertions();
        List<User> users = Users.getUserDetailsByUsername(username);
        int userId = users.get(0).getId();
        List<Post> userPosts = Posts.getPostsByUserId(userId);

        userPosts.forEach(userPost -> {
            softly.assertThat(userPost.getUserId()).as("User id").isNotNull();
            softly.assertThat(userPost.getTitle()).as("User post title").isNotNull();
            softly.assertThat(userPost.getBody()).as("User post body").isNotNull();
            softly.assertThat(userPost.getUserId()).as("User id").isEqualTo(userId);
        });

        softly.assertAll();
    }

    @DataProvider
    public static Object[][] invalidUserId() {
        return new Object[][] {
                { 10000000 },
                { 10000001 }
        };
    }

   @Test(dataProvider = "invalidUserId")
    public void verifySearchingForPostsByInvalidUserIdReturnsAnEmptyArray(int userId) {
       SoftAssertions softly = new SoftAssertions();
       List<Post> userPosts = Posts.getPostsByUserId(userId);

       softly.assertThat(userPosts.size()).as("Number of posts by invalid user").isEqualTo(0);
       softly.assertAll();
    }

    @Test(dataProvider = "validUsername")
    public void verifyEmailFormatInPostComments(String username) {
        SoftAssertions softly = new SoftAssertions();
        List<User> users = Users.getUserDetailsByUsername(username);
        List<Post> userPosts = Posts.getPostsByUserId(users.get(0).getId());

        userPosts.forEach(userPost -> {
            List<Comment> commentsOnUserPost = Comments.getCommentsByPostId(userPost.getId());

            commentsOnUserPost.forEach(comment -> {
                softly.assertThat(comment.getEmail()).as("Email format").matches(emailFormatRegex);
            });
        });

        softly.assertAll();
    }

    @DataProvider
    public static Object[][] invalidPostId() {
        return new Object[][] {
                { 10000000 },
                { 10000001 }
        };
    }

    @Test(dataProvider = "invalidUserId")
    public void verifySearchingForCommentsByInvalidPostIdReturnsAnEmptyArray(int postId) {
        SoftAssertions softly = new SoftAssertions();
        List<Comment> commentsOnUserPost = Comments.getCommentsByPostId(postId);

        softly.assertThat(commentsOnUserPost.size()).as("Number of comments on invalid post").isEqualTo(0);
        softly.assertAll();
    }
}
