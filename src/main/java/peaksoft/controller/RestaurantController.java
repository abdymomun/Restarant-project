package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoRestaurant.RestaurantRequest;
import peaksoft.dto.dtoRestaurant.RestaurantResponse;
import peaksoft.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<SimpleResponse> saveRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        try {
            SimpleResponse response = restaurantService.save(restaurantRequest);
            return ResponseEntity.ok(response);

        }catch (AccessDeniedException e ){
            throw new AccessDeniedException("Access danied !");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
        List<RestaurantResponse> restaurants = restaurantService.getAll();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable Long id) {
        RestaurantResponse restaurant = restaurantService.getById(id);
        return ResponseEntity.ok(restaurant);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SimpleResponse> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequest restaurantRequest) {
        SimpleResponse response = restaurantService.update(id, restaurantRequest);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<SimpleResponse> deleteRestaurant(@PathVariable Long id) {
        SimpleResponse response = restaurantService.delete(id);
        return ResponseEntity.ok(response);
    }
}
