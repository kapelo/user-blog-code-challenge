package components;

import entities.Post;
import util.RequestHandler;
import java.util.Arrays;
import java.util.List;

public class Posts {
    private static final String PATH = "posts";
    private static final String PARAMETER_NAME = "userId"; //Convert to enum

    public static List<Post> getPostsByUserId(int userId) {
        return Arrays.asList(RequestHandler.sendValidGetRequest(PATH, PARAMETER_NAME, userId).as(Post[].class));
    }
}
