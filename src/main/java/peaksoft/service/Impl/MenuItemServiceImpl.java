package peaksoft.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoMenuItem.MenuItemRequest;
import peaksoft.dto.dtoMenuItem.MenuItemResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.StopList;
import peaksoft.entity.SubCategory;
import peaksoft.exception.AccessDeniedException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.repository.template.MenuItemTemplate;
import peaksoft.service.MenuItemService;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemTemplate menuItemTemplate;
    private final SubCategoryRepository subCategoryRepository;
    @Override
    public SimpleResponse save(MenuItemRequest menuItemRequest, Long restaurantId, Long subCategoryId) {
        try {
            MenuItem menuItem = MenuItem.builder()
                    .name(menuItemRequest.getName())
                    .price(menuItemRequest.getPrice())
                    .image(menuItemRequest.getImage())
                    .description(menuItemRequest.getDescription())
                    .isVegatarian(menuItemRequest.isVegatarian())
                    .build();


            Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() ->
                    new NotFoundException("Restaurant with id: " + restaurantId + " not found !"));
            SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() ->
                    new NotFoundException("SubCategory with id: " + subCategoryId + " not found !"));
            menuItem.setRestaurant(restaurant);
            restaurant.setMenuItems(List.of(menuItem));
            restaurant.setMenuItems(List.of(menuItem));
            menuItem.setRestaurant(restaurant);
            menuItem.setSubCategory(subCategory);
            menuItemRepository.save(menuItem);
            return new SimpleResponse(HttpStatus.OK, "Menu item with id: " + menuItem.getId() +
                    " successfully saved to Restaurant with id :" + restaurant.getId());
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public List<MenuItemResponse> getAll(String ascOrDesc) {
        try {
            return menuItemTemplate.getAll(ascOrDesc);
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }catch (NotFoundException e){
            throw new NotFoundException("Not found !");
        }
    }

    @Override
    public List<MenuItemResponse> filterIsVegetarian(boolean isVegetarian) {
        return menuItemRepository.filterByIsVegetarian(isVegetarian);}

    @Override
    public List<MenuItemResponse> search(String letter) {
        try {
            return menuItemRepository.searchMenuItemByName(letter);
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }catch (NotFoundException e){
            throw new NotFoundException("Not found !");
        }
    }

    @Override
    public MenuItemResponse getById(Long id) {
        try {
            return menuItemRepository.getMenuItemsById(id).orElseThrow(() ->
                    new NotFoundException("Menu with id: " + id + " not found !"));
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public SimpleResponse update(Long id, MenuItemRequest menuItemRequest) {
        try {
            MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() ->
                    new NotFoundException("Menu with id: " + id + " not found !"));
            menuItem.setName(menuItemRequest.getName());
            menuItem.setImage(menuItemRequest.getImage());
            menuItem.setPrice(menuItemRequest.getPrice());
            menuItem.setDescription(menuItemRequest.getDescription());
            menuItem.setVegatarian(menuItemRequest.isVegatarian());
            menuItemRepository.save(menuItem);
            return new SimpleResponse(HttpStatus.OK, "Menu with id :" + id + " successfully updated !");
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public SimpleResponse delete(Long id) {
        try {
            MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() ->
                    new NotFoundException("Menu with id: " + id + " not found !"));
            StopList stopList = menuItem.getStopList();
            Restaurant restaurant = menuItem.getRestaurant();
            SubCategory subCategory = menuItem.getSubCategory();
            subCategory.setMenuItem(null);
            menuItem.setSubCategory(null);
            restaurant.setMenuItems(null);
            menuItem.setRestaurant(null);
            stopList.setMenuItem(null);
            menuItem.setStopList(null);

            menuItemRepository.delete(menuItem);
            return new SimpleResponse(HttpStatus.OK, "Menu with id :" + id + " successfully deleted !");
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }
}
