package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import peaksoft.validation.NotBlank;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "menu_items")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItem {
    @Id
    @GeneratedValue(generator = "menuItem_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "menuItem_gen",sequenceName = "menuItem_seq",allocationSize = 1)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String image;
    @NotNull
    private BigDecimal price;
    @NotNull
    private String description;
    @NotNull
    @Column(name = "is_vegatarian")
    private boolean isVegatarian;
    @ManyToMany
    private List<Cheque> cheques;
    @ManyToOne
    private Restaurant restaurant;
    @OneToOne
    private StopList stopList;
    @ManyToOne
    private SubCategory subCategory;
}

