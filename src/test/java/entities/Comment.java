package entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder({"PostId", "Id", "Name", "Email", "Body"})
@Data
public class Comment {
    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;
}
