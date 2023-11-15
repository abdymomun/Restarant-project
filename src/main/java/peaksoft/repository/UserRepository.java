package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.entity.User;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    Optional<User> getUserByEmail(String email);
    @Query("select count(u.id) from Restaurant  r" +
            " join User u on r.id = u.restaurant.id")
    Long getCountEmployees();
}
