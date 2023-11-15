package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(generator = "restaurant_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "restaurant_gen",sequenceName = "restaurant_seq",allocationSize = 1)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String location;
    @NotNull
    private String restType;
    private Long numberOfEmployees;
    @NotNull
    private int service;
    @OneToMany(mappedBy = "restaurant")
    private List<User>users;
    @OneToMany(mappedBy = "restaurant")
    private List<MenuItem>menuItems;
}
