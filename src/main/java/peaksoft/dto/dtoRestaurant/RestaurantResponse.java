package peaksoft.dto.dtoRestaurant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter@Setter
@Builder
public class RestaurantResponse {
    private Long id;
    private String name;
    private String location;
    private String restType;
    private int numberOfEmployees;
    private int service;


    public RestaurantResponse(Long id, String name, String location, String restType, int numberOfEmployees, int service) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.restType = restType;
        this.numberOfEmployees = numberOfEmployees;
        this.service = service;
    }
}
