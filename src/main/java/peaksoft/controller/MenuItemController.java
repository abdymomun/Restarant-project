package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoMenuItem.MenuItemRequest;
import peaksoft.dto.dtoMenuItem.MenuItemResponse;
import peaksoft.service.MenuItemService;

import java.util.List;
@RestController
@RequestMapping("/api/menuitems")
public class MenuItemController {

    private final MenuItemService menuItemService;

    @Autowired
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping("/save/{restaurantId}/{subCategoryId}")
    public ResponseEntity<SimpleResponse> saveMenuItem(@RequestBody MenuItemRequest menuItemRequest,
                                                       @PathVariable Long restaurantId,
                                                       @PathVariable Long subCategoryId ) {
        SimpleResponse response = menuItemService.save(menuItemRequest, restaurantId,subCategoryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MenuItemResponse>> getAllMenuItems(@RequestParam(name = "ascOrDesc", defaultValue = "asc") String ascOrDesc) {
        List<MenuItemResponse> menuItems = menuItemService.getAll(ascOrDesc);
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/search/{letter}")
    public ResponseEntity<List<MenuItemResponse>> searchMenuItems(@PathVariable String letter) {
        List<MenuItemResponse> menuItems = menuItemService.search(letter);
        return ResponseEntity.ok(menuItems);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/filter/{filter}")
    public List<MenuItemResponse> filterVegetarian(@PathVariable boolean filter){
       return menuItemService.filterIsVegetarian(filter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponse> getMenuItemById(@PathVariable Long id) {
        MenuItemResponse menuItem = menuItemService.getById(id);
        return ResponseEntity.ok(menuItem);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
    public ResponseEntity<SimpleResponse> updateMenuItem(@PathVariable Long id, @RequestBody MenuItemRequest menuItemRequest) {
        SimpleResponse response = menuItemService.update(id, menuItemRequest);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<SimpleResponse> deleteMenuItem(@PathVariable Long id) {
        SimpleResponse response = menuItemService.delete(id);
        return ResponseEntity.ok(response);
    }
}

