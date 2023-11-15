package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import peaksoft.validation.NotBlank;

import java.time.LocalDate;
@Entity
@Table(name = "stop_lists")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StopList {
    @Id
    @GeneratedValue(generator = "stopList_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "stopList_gen",sequenceName = "stopList_seq",allocationSize = 1)
    private Long id;
    @NotNull
    private String reason;
    @NotNull
    private LocalDate date;
    @OneToOne
    private MenuItem menuItem;
}
