package peaksoft.dto.dtoCheque;

import lombok.Getter;
import lombok.Setter;
@Getter@Setter
public class ChequeToday {
    private int priceAverage;
    public ChequeToday(int priceAverage) {
        this.priceAverage = priceAverage;
    }
}
