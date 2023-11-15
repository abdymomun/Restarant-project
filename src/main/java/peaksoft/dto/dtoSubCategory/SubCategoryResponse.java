package peaksoft.dto.dtoSubCategory;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Builder
public class SubCategoryResponse {
    private Long id;
    private String name;
    private String categoryName;

    public SubCategoryResponse(Long id, String name,  String categoryName) {
        this.id = id;
        this.name = name;
        this.categoryName = categoryName;
    }
}
