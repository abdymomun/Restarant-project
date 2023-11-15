package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import peaksoft.validation.NotBlank;

import java.util.List;

@Entity
@Table(name = "sub_categories")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategory {
    @Id
    @GeneratedValue(generator = "subCategory_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "subCategory_gen",sequenceName = "subCategory_seq",allocationSize = 1)
    private Long id;
    @NotNull
    private String name;
    @OneToMany(mappedBy = "subCategory")
    private List<MenuItem> menuItem;
    @ManyToOne
    private Category category;
}
