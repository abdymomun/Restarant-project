package peaksoft.dto.dtoUser;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Role;
import peaksoft.validation.NotBlank;

import java.time.LocalDate;
@Getter
@Setter
@Builder
public class UserRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private LocalDate dateOfBirth;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private Role role;
    @NotBlank
    private int expirense;

}
