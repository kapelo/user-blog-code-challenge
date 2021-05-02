package entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder({"street", "suite", "city", "zipcode", "geo"})
@Data
class Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo geo;
}
