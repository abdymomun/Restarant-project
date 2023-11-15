package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoUser.*;
import java.util.List;

public interface UserService {
    AuthenticationResponse signUp(SignUpRequest signUpRequest);
    AuthenticationResponse signIn(SignInRequest signInRequest);
    SimpleResponse acceptUser(Long restaurantId, Long userId,String word);
    List<GetJobs> getJobsFromRestaurant();
    List<UserResponse> getAll();
    UserResponse getById(Long id);
    SimpleResponse update(UserRequest userRequest);
    SimpleResponse updateUser(Long userId,UserRequest userRequest);
    SimpleResponse goOut();
    SimpleResponse deleteUser(Long userId);
    void init();

}
