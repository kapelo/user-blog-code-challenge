package entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder({"lat", "lng"})
@Data
class Geo {
    private String lat;
    private String lng;
}
