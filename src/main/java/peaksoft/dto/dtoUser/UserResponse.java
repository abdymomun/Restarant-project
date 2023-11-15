package peaksoft.dto.dtoUser;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Role;
import peaksoft.entity.Cheque;
import peaksoft.entity.Restaurant;

import java.time.LocalDate;
import java.util.List;
@Getter@Setter
@Builder
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
    private int expirense;
    private Restaurant restaurant;
    private List<Cheque> cheques;

    public Long getId() {
        return id;
    }



    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


}
