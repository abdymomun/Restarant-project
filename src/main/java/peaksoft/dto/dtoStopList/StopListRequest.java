package peaksoft.dto.dtoStopList;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.validation.NotBlank;

import java.time.LocalDate;
@Getter@Setter
@Builder
public class StopListRequest {
    @NotBlank
    private String reason;
    private LocalDate date;
    private Long menuItemId;
}
