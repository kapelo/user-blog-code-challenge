package components;

import entities.User;
import util.RequestHandler;
import java.util.Arrays;
import java.util.List;

public class Users {
    private static final String PATH = "users";
    private static final String PARAMETER_NAME = "username"; //Convert to enum

    public static List<User> getUserDetailsByUsername(String username) {
        return Arrays.asList(
                RequestHandler.sendValidGetRequest(PATH, PARAMETER_NAME, username).as(User[].class)
        );
    }

    public static List<User> getAllUsers() {
        return Arrays.asList(
                RequestHandler.sendValidGetRequest(PATH).as(User[].class)
        );
    }
}
