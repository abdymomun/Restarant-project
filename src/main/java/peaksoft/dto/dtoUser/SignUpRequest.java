package peaksoft.dto.dtoUser;

import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Role;
import peaksoft.validation.NotBlank;
import peaksoft.validation.PasswordValid;
import peaksoft.validation.PhoneNumberValid;

import java.time.LocalDate;
@Getter @Setter
public class SignUpRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private LocalDate dateOfBirth;
    @NotBlank
    private String email;
    @NotBlank
    @PasswordValid
    private String password;
    @NotBlank
    private Role role;
    @NotBlank
    @PhoneNumberValid
    private String phoneNumber;
    @NotBlank
    private int expirense;
}
