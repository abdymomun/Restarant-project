package peaksoft.dto.dtoCategory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.validation.NotBlank;

@Getter@Setter
@Builder
public class CategoryRequest {
    @NotBlank
    private String name;
    @JsonCreator
    public CategoryRequest(@JsonProperty("name") String name) {
        this.name = name;
    }
}
