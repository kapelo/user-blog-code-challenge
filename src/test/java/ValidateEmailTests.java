import components.Comments;
import components.Posts;
import components.Users;
import entities.Comment;
import entities.Post;
import entities.User;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.Arrays;
import java.util.List;

public class ValidateEmailTests {
    private int userId;
    private String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

    @DataProvider
    public static Object[][] usernames() {
        return new Object[][] {
                { "Delphine"}
        };
    }

    @Test(dataProvider = "usernames")
    public void verifyUsersUsername(String username) {
        List<User> users = Arrays.asList(Users.getUser(username).as(User[].class));
        User user = users.get(0);
        userId = user.getId();

        Assert.assertEquals(user.getUsername(), username);
    }

    @Test(dataProvider = "usernames")
    public void verifyAllUsersHaveUsername(String username) {
        Users.getAllUsers()
                .then()
                .assertThat()
                .body(Matchers.containsStringIgnoringCase(username));
    }

    @Test(dataProvider = "usernames")
    public void verifyThereAreMoreThanZeroUsers(String username) {
        List<User> users = Arrays.asList(Users.getAllUsers().as(User[].class));
        int size = users.size();

        Assert.assertNotEquals(size, 0);
    }

    @Test(dependsOnMethods = "verifyUsersUsername")
    public void verifyEmailFormatInPostComments() {
        SoftAssert softAssert = new SoftAssert();
        List<Post> userPosts = Arrays.asList(Posts.getUserPostsById(userId).as(Post[].class));

        userPosts.forEach(userPost -> {
            Assert.assertEquals(userPost.getUserId(), userId);
            List<Comment> commentsOnUserPost = Arrays.asList(Comments.getCommentsByPostId(userPost.getId()).as(Comment[].class));

            commentsOnUserPost.forEach(comment -> {
                softAssert.assertTrue(comment.getEmail().matches(emailRegex));
            });
        });

        softAssert.assertAll();
    }
}
