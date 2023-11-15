package peaksoft.dto.dtoCheque;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Getter@Setter
@NoArgsConstructor
@Builder
public class ChequeResponse {
    private Long id;
    private String restaurantName;
    private String waiterFullName;
    private List<String>menuName;
    private BigDecimal priceAverage;
    private int service;
    private BigDecimal grandTotal;

    public ChequeResponse(Long  id,String restaurantName, String waiterFullName, List<String> menuName, BigDecimal priceAverage, int service, BigDecimal grandTotal) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.waiterFullName = waiterFullName;
        this.menuName = menuName;
        this.priceAverage = priceAverage;
        this.service = service;
        this.grandTotal = grandTotal;
    }
}
