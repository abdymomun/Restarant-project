package peaksoft.dto.dtoCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private List<String> subCategoryName;

    public CategoryResponse(Long id, String name, List<String> subCategoryName) {
        this.id = id;
        this.name = name;
        this.subCategoryName = subCategoryName;
    }
}
