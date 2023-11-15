package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoMenuItem.MenuItemRequest;
import peaksoft.dto.dtoMenuItem.MenuItemResponse;

import java.util.List;

public interface MenuItemService {
    SimpleResponse save(MenuItemRequest menuItemRequest,Long restaurantId,Long subCategoryId);
    List<MenuItemResponse>getAll(String ascOrDesc);
    List<MenuItemResponse>search(String letter);
    List<MenuItemResponse>filterIsVegetarian(boolean isVegetarian);
    MenuItemResponse getById(Long id);
    SimpleResponse update(Long id,MenuItemRequest menuItemRequest);
    SimpleResponse delete(Long id);
}
