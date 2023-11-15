package peaksoft.dto.dtoStopList;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter@Setter
@Builder
public class StopListResponse {
    private Long id;
    private String reason;
    private LocalDate date;
    private Long menuItemId;

    public StopListResponse(Long id, String reason, LocalDate date, Long menuItemId) {
        this.id = id;
        this.reason = reason;
        this.date = date;
        this.menuItemId = menuItemId;
    }
}
