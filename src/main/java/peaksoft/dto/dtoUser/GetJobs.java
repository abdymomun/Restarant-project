package peaksoft.dto.dtoUser;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Role;

import java.time.LocalDate;
@Getter @Setter
@Builder
public class GetJobs {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
    private int expirense;

    public GetJobs(Long id, String firstName, String lastName, LocalDate dateOfBirth, String email, String password, String phoneNumber, Role role, int expirense) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.expirense = expirense;
    }
}
