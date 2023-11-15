package peaksoft.dto.dtoRestaurant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.validation.NotBlank;

@Getter@Setter
@Builder
public class RestaurantRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String location;
    @NotBlank
    private String restType;
    @NotBlank
    private int service;
}
