package entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder({"name", "catchPhrase", "bs"})
@Data
class Company {
    private String name;
    private String catchPhrase;
    private String bs;
}
