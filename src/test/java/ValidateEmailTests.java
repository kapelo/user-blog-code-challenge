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
    List<Post> userPosts;

    @DataProvider
    public static Object[][] invalidUsername() {
        return new Object[][] {
                { "fakeusername1" },
                { "fakeusername2" }
        };
    }

    @Test(dataProvider = "invalidUsername")
    public void verifyInvalidUsernameReturnsAnEmptyArray(String username) {
        List<User> users = Arrays.asList(Users.getUser(username).as(User[].class));
        int size = users.size();

        Assert.assertEquals(size, 0);
    }

    @DataProvider
    public static Object[][] validUsername() {
        return new Object[][] {
                { "Delphine" }
        };
    }

    @Test(dataProvider = "validUsername")
    public void verifyUsersUsername(String username) {
        List<User> users = Arrays.asList(Users.getUser(username).as(User[].class));
        User user = users.get(0);
        userId = user.getId();

        Assert.assertEquals(user.getUsername(), username);
    }

    @Test(dataProvider = "validUsername")
    public void verifyAllUsersHaveUsername(String username) {
        Users.getAllUsers()
                .then()
                .assertThat()
                .body(Matchers.containsStringIgnoringCase(username));
    }

    @Test
    public void verifyThereAreMoreThanZeroUsers() {
        List<User> users = Arrays.asList(Users.getAllUsers().as(User[].class));
        int size = users.size();

        Assert.assertNotEquals(size, 0);
    }

    @Test(dependsOnMethods = "verifyUsersUsername")
    public void verifyPostsByUser() {
        SoftAssert softAssert = new SoftAssert();
        userPosts = Arrays.asList(Posts.getUserPostsById(userId).as(Post[].class));

        userPosts.forEach(userPost -> {
            softAssert.assertEquals(userPost.getUserId(), userId);
        });

        softAssert.assertAll();
    }

    @Test
    public void verifySearchingForPostsByInvalidUserIdReturnsAnEmptyArray() {
        userPosts = Arrays.asList(Posts.getUserPostsById(10000001).as(Post[].class));
        int size = userPosts.size();

        Assert.assertEquals(size, 0);
    }

    @Test(dependsOnMethods = { "verifyUsersUsername", "verifyPostsByUser" })
    public void verifyEmailFormatInPostComments() {
        SoftAssert softAssert = new SoftAssert();

        userPosts.forEach(userPost -> {
            List<Comment> commentsOnUserPost = Arrays.asList(Comments.getCommentsByPostId(userPost.getId()).as(Comment[].class));

            commentsOnUserPost.forEach(comment -> {
                softAssert.assertTrue(comment.getEmail().matches(emailRegex));
            });
        });

        softAssert.assertAll();
    }

    @Test
    public void verifySearchingForCommentsByInvalidPostIdReturnsAnEmptyArray() {
        List<Comment> commentsOnUserPost = Arrays.asList(Comments.getCommentsByPostId(10000001).as(Comment[].class));

        int size = commentsOnUserPost.size();

        Assert.assertEquals(size, 0);
    }
}
