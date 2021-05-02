package components;

import entities.Comment;
import util.RequestHandler;
import java.util.Arrays;
import java.util.List;

public class Comments {
    private static final String PATH = "comments";
    private static final String PARAMETER_NAME = "postId"; //Convert to enum

    public static List<Comment> getCommentsByPostId(int postId) {
        return Arrays.asList(RequestHandler.sendValidGetRequest(PATH, PARAMETER_NAME, postId).as(Comment[].class));
    }
}
