package peaksoft.dto.dtoCheque;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter@Setter
@Builder
public class ChequeRequest {
    private List<Long> menuItemId;
    @JsonCreator
    public ChequeRequest(@JsonProperty("menuItemId") List<Long> menuItemId) {
        this.menuItemId = menuItemId;
    }
}
