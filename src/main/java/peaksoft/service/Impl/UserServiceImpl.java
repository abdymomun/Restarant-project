package peaksoft.service.Impl;

import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.entity.Cheque;
import peaksoft.enums.Role;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoUser.*;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.exception.*;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.security.JwtService;
import peaksoft.service.UserService;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    @Override
    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {

            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                throw new AlreadyExistException("User with email: " + signUpRequest.getEmail() + " already exist !");
            }

        User user = new User();
            user.setFirstName(signUpRequest.getFirstName());
            user.setLastName(signUpRequest.getLastName());

            LocalDate currentDate = LocalDate.now();
            LocalDate dob = signUpRequest.getDateOfBirth();
            int age = Period.between(dob, currentDate).getYears();
            if (signUpRequest.getRole().equals(Role.WAITER)) {
                if (age > 18 && age < 30) {
                    user.setDateOfBirth(signUpRequest.getDateOfBirth());
                } else throw new InvalidAgeException("Waiter age must be between 18 and 50 !");
            } else if (signUpRequest.getRole().equals(Role.CHEF)) {
                if (age > 25 && age < 45) {
                    user.setDateOfBirth(signUpRequest.getDateOfBirth());
                } else throw new InvalidAgeException("Chef age must be between 25 and 45 !");
            }
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            user.setPhoneNumber(signUpRequest.getPhoneNumber());
            user.setRole(signUpRequest.getRole());
            if (signUpRequest.getRole().equals(Role.WAITER)) {
                if (signUpRequest.getExpirense() >= 1) {
                    user.setExpirense(signUpRequest.getExpirense());
                } else throw new ExpirenseException("Waiter experience should be more than 1 year ");
            }
            if (signUpRequest.getRole().equals(Role.CHEF)) {
                if (signUpRequest.getExpirense() >= 2) {
                    user.setExpirense(signUpRequest.getExpirense());
                } else throw new ExpirenseException("Chef experience should be more than 2 year ");
            }
        Long countEmployees = userRepository.getCountEmployees();
        long count = userRepository.count();
            if (count >= 3) {
                throw new MaxUserLimitReachedException("No vacation !");
            }

            try {
                if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                    throw new EmailNotUniqueException("User with email: " + signUpRequest.getEmail() + " already exists !! ");

                }
                userRepository.save(user);
            } catch (ConstraintViolationException ex) {
                throw new EmailNotUniqueException("User with email: " + signUpRequest.getEmail() + " already exists!");
            }


            String token = jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(token)
                    .email(user.getEmail()).role(user.getRole()).build();
        }




    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.getUserByEmail(signInRequest.getEmail()).orElseThrow(() ->
                new NotFoundException("User with email: " + signInRequest.getEmail() + " not found !"));
        if (!userRepository.existsByEmail(signInRequest.getEmail())){
            throw new NotFoundException("User with email: " + signInRequest.getEmail() + " not found ! ");
        }
        long count = userRepository.count();
        if (count >= 15){
            throw new MaxUserLimitReachedException("No vacation !");
        }
        else if (signInRequest.getPassword().isBlank()) {
            throw new BadCredentialException("Password should not be empty !");
        } else if (signInRequest.getEmail().isBlank()){
            throw new BadCredentialException("Email should not be empty ! ");
        } else if (!passwordEncoder.matches(signInRequest.getPassword(),user.getPassword())){
            throw new BadCredentialException("Wrong password !");
        } if (passwordEncoder.matches(signInRequest.getPassword(),user.getPassword())) {
            String token = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(token)
                    .email(user.getEmail())
                    .role(user.getRole()).build();
        }
        return null;
    }

    @Override
    public SimpleResponse acceptUser(Long restaurantId, Long userId, String word) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() ->
                    new NotFoundException("User with id: " + userId + " not found ! "));
            Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() ->
                    new NotFoundException("Restaurant with id: " + restaurantId + " not found !"));
            long count = userRepository.count();
            Long countEmployees = userRepository.getCountEmployees();
            if (word.equalsIgnoreCase("accepted")) {
                restaurant.getUsers().add(user);
                restaurant.setNumberOfEmployees(countEmployees);
                restaurantRepository.save(restaurant);
                user.setRestaurant(restaurant);
                userRepository.save(user);

                return new SimpleResponse(HttpStatus.OK, "Successfully accepted !");
            }

            if (count >= 15) {
                throw new MaxUserLimitReachedException("No vacation !");
            }
            if (word.equalsIgnoreCase("refusal")) {
                userRepository.delete(user);
                return new SimpleResponse(HttpStatus.OK, "Successfully deleted !");
            }

        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
        return null;
    }

    @Override
    public List<GetJobs> getJobsFromRestaurant() {
        try {
            List<Restaurant> restaurants = restaurantRepository.findAll();
            Restaurant restaurant = restaurants.get(0);
            List<User> users = restaurant.getUsers();

            Long excludedUserId = 1L;

            List<GetJobs> userResponses = new ArrayList<>();

            for (User user : users) {
                if (!Objects.equals(user.getId(), excludedUserId)) {
                    GetJobs userResponse = GetJobs.builder()
                            .id(user.getId())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .dateOfBirth(user.getDateOfBirth())
                            .phoneNumber(user.getPhoneNumber())
                            .email(user.getEmail())
                            .role(user.getRole())
                            .expirense(user.getExpirense())
                            .build();
                    userResponses.add(userResponse);
                }
            }
            return userResponses;
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Need a token " + a.getMessage());
        }

    }


    @Override
    public List<UserResponse> getAll() {
        try {
            List<User> users = userRepository.findAll();
            List<UserResponse> userResponses = new ArrayList<>();
            users.forEach(user -> {
                UserResponse userResponse = UserResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .dateOfBirth(user.getDateOfBirth())
                        .phoneNumber(user.getPhoneNumber())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .expirense(user.getExpirense())
                        .build();
                userResponses.add(userResponse);
            });
            return userResponses;
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public UserResponse getById(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() ->
                    new NotFoundException("User with id: " + id + " not found ! "));
            return UserResponse.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .dateOfBirth(user.getDateOfBirth())
                    .phoneNumber(user.getPhoneNumber())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .expirense(user.getExpirense())
                    .build();
        } catch (AccessDeniedException a) {
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }
    @Override
    public SimpleResponse update(UserRequest userRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                    new NotFoundException("User with email: " + email + " not found !"));
            user.setFirstName(user.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setRole(userRequest.getRole());
            user.setEmail(userRequest.getEmail());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user.setPhoneNumber(userRequest.getPhoneNumber());
            user.setExpirense(userRequest.getExpirense());
            user.setDateOfBirth(userRequest.getDateOfBirth());
            user.setRole(userRequest.getRole());
            userRepository.save(user);
            return new SimpleResponse(HttpStatus.OK, "User with id: " + user.getId() + " successfully updated !");
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public SimpleResponse updateUser(Long userId, UserRequest userRequest) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() ->
                    new NotFoundException("User with id: " + userId + " not found ! "));
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setRole(userRequest.getRole());
            user.setEmail(userRequest.getEmail());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user.setPhoneNumber(userRequest.getPhoneNumber());
            user.setExpirense(userRequest.getExpirense());
            user.setDateOfBirth(userRequest.getDateOfBirth());
            user.setRole(userRequest.getRole());
            userRepository.save(user);
            return new SimpleResponse(HttpStatus.OK, "User with id: " + user.getId() + " successfully updated !");
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public SimpleResponse goOut() {
        try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(()->
                new NotFoundException("User with email: " + email + " not found !"));
            Restaurant restaurant = user.getRestaurant();
            List<Cheque> cheques = user.getCheques();
            for (Cheque cheque : cheques) {
                cheque.setUser(null);
                user.setCheques(null);
            }
            user.setRestaurant(null);
            restaurant.setUsers(null);
        userRepository.delete(user);
        return new SimpleResponse(HttpStatus.OK,"User with id: " + user.getId() + " successfully deleted !");
    }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public SimpleResponse deleteUser(Long userId) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() ->
                    new NotFoundException("User with id: " + userId + " not found ! "));
            Restaurant restaurant = user.getRestaurant();
            List<Cheque> cheques = user.getCheques();
            for (Cheque cheque : cheques) {
                cheque.setUser(null);
                user.setCheques(null);
            }
            user.setRestaurant(null);
            restaurant.setUsers(null);
            userRepository.delete(user);
            return new SimpleResponse(HttpStatus.OK, "User with id: " + user.getId() + " successfully deleted !");
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    @PostConstruct
    public void init() {
        User adminRequest = new User();
        adminRequest.setFirstName("Admin");
        adminRequest.setLastName("Admin");
        adminRequest.setEmail("admin@gmail.com");
        adminRequest.setPassword(passwordEncoder.encode("admin00000"));
        adminRequest.setPhoneNumber("+996700000000");
        adminRequest.setRole(Role.ADMIN);
        adminRequest.setDateOfBirth(LocalDate.of(1994,11,13));
        adminRequest.setExpirense(1);
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            userRepository.save(adminRequest);
        }
    }
}
