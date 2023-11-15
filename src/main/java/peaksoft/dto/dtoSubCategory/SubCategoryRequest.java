package peaksoft.dto.dtoSubCategory;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.validation.NotBlank;

@Getter@Setter
@Builder
public class SubCategoryRequest {
    private Long id;
    @NotBlank
    private String name;
}
