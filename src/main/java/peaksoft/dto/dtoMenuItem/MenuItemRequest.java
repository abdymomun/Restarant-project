package peaksoft.dto.dtoMenuItem;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.validation.NotBlank;

import java.math.BigDecimal;
@Getter@Setter
@Builder
public class MenuItemRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String image;
    @NotBlank
    private BigDecimal price;
    @NotBlank
    private String description;
    @NotBlank
    private boolean isVegatarian;
}
