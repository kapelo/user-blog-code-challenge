package entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder({"UserId", "Id", "Title", "Body"})
@Data
public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;
}
