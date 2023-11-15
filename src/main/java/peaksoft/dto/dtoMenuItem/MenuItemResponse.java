package peaksoft.dto.dtoMenuItem;

import lombok.*;
import java.math.BigDecimal;
@Getter@Setter
@NoArgsConstructor
@Builder
public class MenuItemResponse {
    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
    private String description;
    private boolean isVegatarian;
    private String  restaurantName;
    private String  subCategoryName;

    public MenuItemResponse(Long id, String name, String image, BigDecimal price, String description, boolean isVegatarian, String restaurantName, String subCategoryName) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegatarian = isVegatarian;
        this.restaurantName = restaurantName;
        this.subCategoryName = subCategoryName;
    }

}
