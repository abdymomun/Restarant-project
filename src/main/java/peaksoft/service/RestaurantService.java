package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoRestaurant.RestaurantRequest;
import peaksoft.dto.dtoRestaurant.RestaurantResponse;

import java.util.List;

public interface RestaurantService {
    SimpleResponse save(RestaurantRequest restaurantRequest);
    List<RestaurantResponse>getAll();
    RestaurantResponse getById(Long id);
    SimpleResponse update(Long id,RestaurantRequest restaurantRequest);
    SimpleResponse delete(Long id);

}
