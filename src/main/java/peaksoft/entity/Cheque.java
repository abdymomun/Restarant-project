package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cheques")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cheque  {
    @Id
    @GeneratedValue(generator = "cheque_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "cheque_gen",sequenceName = "cheque_seq",allocationSize = 1)
    private Long id;
    @Column(name = "price_average")
    private BigDecimal priceAverage;
    @Column(name = "create_at")
    private LocalDate createdAt;
    @ManyToOne
    private User user;
    @ManyToMany(mappedBy = "cheques")
    private List<MenuItem> menuItems;
}
