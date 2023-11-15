package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.dtoMenuItem.MenuItemResponse;
import peaksoft.entity.MenuItem;
import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {

    @Query("select new peaksoft.dto.dtoMenuItem.MenuItemResponse(m.id, m.name, m.image, m.price, m.description, m.isVegatarian, r.name, s.name) " +
            "from MenuItem m " +
            "join SubCategory s  on s.id = m.subCategory.id " +
            "join Restaurant r on r.id = m.restaurant.id " +
            "where m.name ilike concat(:letter, '%') " +
            "   or m.name ilike concat('%', :letter, '%') " +
            "   or s.name ilike concat('%', :letter, '%') ")
    List<MenuItemResponse> searchMenuItemByName(@Param("letter") String letter);

    @Query("select new peaksoft.dto.dtoMenuItem.MenuItemResponse(m.id, m.name, m.image, m.price, m.description, m.isVegatarian, r.name, s.name) " +
            "from MenuItem m " +
            "join m.subCategory s " +
            "join Restaurant r on r.id = m.restaurant.id " +
            "where m.id = :id")
    Optional<MenuItemResponse> getMenuItemsById(@Param("id") Long id);

    @Query("select new peaksoft.dto.dtoMenuItem.MenuItemResponse(m.id, m.name, m.image, m.price, m.description, m.isVegatarian, r.name, s.name) " +
            "from MenuItem m " +
            "join m.subCategory s " +
            "join Restaurant r on r.id = m.restaurant.id " +
            "where m.isVegatarian = :isVegatarian")
    List<MenuItemResponse> filterByIsVegetarian(@Param("isVegatarian") boolean isVegatarian);


    boolean existsById(Long id);
}
