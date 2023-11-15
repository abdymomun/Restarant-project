package peaksoft.service.Impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoRestaurant.RestaurantRequest;
import peaksoft.dto.dtoRestaurant.RestaurantResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exception.AccessDeniedException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

//    private boolean userHasAccess() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//         authentication.getAuthorities().stream()
//                .anyMatch(authority -> authority.getAuthority().equals(Role.valueOf("ADMIN")));
//    }
    @Override
    public SimpleResponse save(RestaurantRequest restaurantRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            if (!email.contains("admin@gmail.com")){
                throw new AccessDeniedException("Access denied.");
            }

            Restaurant restaurant = Restaurant.builder()
                    .name(restaurantRequest.getName())
                    .restType(restaurantRequest.getRestType())
                    .location(restaurantRequest.getLocation())
                    .service(restaurantRequest.getService())
                    .build();

            User user = userRepository.findById(1L).orElseThrow();
            restaurant.setUsers(List.of(user));
            user.setRestaurant(restaurant);
            restaurantRepository.save(restaurant);
            return new SimpleResponse(HttpStatus.OK, "Restaurant with id :" + restaurant.getId() + " successfully saved !");
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied ");
        }
    }

    @Override
    public List<RestaurantResponse> getAll() {
        try {
            List<Restaurant> restaurants = restaurantRepository.findAll();
            List<RestaurantResponse> restaurantResponses = new ArrayList<>();
            restaurants.forEach(restaurant -> {
                List<User> users = restaurant.getUsers();
                int size = users.size();
                RestaurantResponse response = RestaurantResponse.builder()
                        .id(restaurant.getId())
                        .name(restaurant.getName())
                        .restType(restaurant.getRestType())
                        .location(restaurant.getLocation())
                        .service(restaurant.getService())
                        .numberOfEmployees(size)
                        .build();
                restaurantResponses.add(response);
            });
            return restaurantResponses;
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public RestaurantResponse getById(Long id) {
        try {
            Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() ->
                    new NotFoundException("Restaurant with id: " + id + " not found !"));
            List<User> users = restaurant.getUsers();
            int size = users.size();
            return RestaurantResponse.builder()
                    .id(restaurant.getId())
                    .name(restaurant.getName())
                    .restType(restaurant.getRestType())
                    .location(restaurant.getLocation())
                    .service(restaurant.getService())
                    .numberOfEmployees(size)
                    .build();
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public SimpleResponse update(Long id, RestaurantRequest restaurantRequest) {
        try {
            Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() ->
                    new NotFoundException("Restaurant with id: " + id + " not found !"));
            restaurant.setName(restaurantRequest.getName());
            restaurant.setRestType(restaurantRequest.getRestType());
            restaurant.setLocation(restaurantRequest.getLocation());
            restaurant.setService(restaurantRequest.getService());
            restaurantRepository.save(restaurant);
            return new SimpleResponse(HttpStatus.OK, "Restaurant with id: " + id + " successfully updated !");
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public SimpleResponse delete(Long id) {
        try {
            Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() ->
                    new NotFoundException("Restaurant with id: " + id + " not found !"));
            List<MenuItem> menuItems = restaurant.getMenuItems();
            List<User> users = restaurant.getUsers();
            for (MenuItem menuItem : menuItems) {
                menuItem.setRestaurant(null);
                restaurant.setMenuItems(null);
            }
            for (User user : users) {
                user.setRestaurant(null);
                restaurant.setUsers(null);
            }
            restaurantRepository.delete(restaurant);
            return new SimpleResponse(HttpStatus.OK, "Restaurant with id: " + id + " successfully deleted !");
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }
}
