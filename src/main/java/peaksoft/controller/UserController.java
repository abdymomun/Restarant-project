package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoUser.*;
import peaksoft.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        AuthenticationResponse response = userService.signUp(signUpRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest) {
        AuthenticationResponse response = userService.signIn(signInRequest);
        return ResponseEntity.ok(response);
    }

@PreAuthorize("hasAuthority('ADMIN')")
@PutMapping("/accept/{restaurantId}/{userId}/{word}")
public ResponseEntity<SimpleResponse> acceptUser(
        @PathVariable Long restaurantId,
        @PathVariable Long userId,
        @PathVariable String word
) {
    SimpleResponse response = userService.acceptUser(restaurantId, userId, word);
    return ResponseEntity.ok(response);
}

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAll();
        return ResponseEntity.ok(users);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getById(id);
        return ResponseEntity.ok(user);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/jobs")
    public ResponseEntity<List<GetJobs>> getUserService() {
        List<GetJobs> users = userService.getJobsFromRestaurant();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update")
    public ResponseEntity<SimpleResponse> update(@RequestBody UserRequest userRequest) {
        SimpleResponse response = userService.update(userRequest);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<SimpleResponse> updateUser(@RequestBody UserRequest userRequest, @PathVariable Long id) {
        SimpleResponse response = userService.updateUser(id,userRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/out")
    public ResponseEntity<SimpleResponse> out() {
        SimpleResponse response = userService.goOut();
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<SimpleResponse> deleteUser(@PathVariable Long userId) {
        SimpleResponse response = userService.deleteUser(userId);
        return ResponseEntity.ok(response);
    }
}
