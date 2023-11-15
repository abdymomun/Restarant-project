package peaksoft.repository.template;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import peaksoft.dto.dtoMenuItem.MenuItemResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class MenuItemTemplate {
    private final JdbcTemplate jdbcTemplate;

    public List<MenuItemResponse> searchByLetter(String letter) {
        String sql = "SELECT mi.id, mi.name, mi.image, mi.price, mi.description, mi.is_vegatarian, r.name AS restaurant_name, sc.name AS sub_category_name " +
                "FROM menu_items mi " +
                "JOIN restaurants r ON r.id = mi.restaurant_id " +
                "JOIN sub_categories sc ON sc.id = mi.sub_category_id where mi.name LIKE ?";
        String searchPattern = letter + "%";

        return jdbcTemplate.query(sql, new Object[]{searchPattern}, new RowMapper<MenuItemResponse>() {
            @Override
            public MenuItemResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                MenuItemResponse menuItemResponse = new MenuItemResponse();
                menuItemResponse.setId(rs.getLong("id"));
                menuItemResponse.setName(rs.getString("name"));
                menuItemResponse.setImage(rs.getString("image"));
                menuItemResponse.setPrice(rs.getBigDecimal("price"));
                menuItemResponse.setDescription(rs.getString("description"));
                menuItemResponse.setVegatarian(rs.getBoolean("is_vegatarian"));
                menuItemResponse.setRestaurantName(rs.getString("restaurant_name"));
                menuItemResponse.setSubCategoryName(rs.getString("sub_category_name"));
                return menuItemResponse;
            }
        });
    }
    public List<MenuItemResponse> filterByVegetarian(boolean isVegetarian) {
        String sql = "SELECT mi.id, mi.name, mi.image, mi.price, mi.description, mi.is_vegatarian, r.name AS restaurant_name, sc.name AS sub_category_name " +
                "FROM menu_items mi " +
                "JOIN restaurants r ON r.id = mi.restaurant_id " +
                "JOIN sub_categories sc ON sc.id = mi.sub_category_id " +
                "WHERE mi.is_vegatarian = ?";

        return jdbcTemplate.query(
                sql,
                new Object[]{isVegetarian},
                (rs, rowNum) -> {
                    MenuItemResponse menuItemResponse = new MenuItemResponse();
                    menuItemResponse.setId(rs.getLong("id"));
                    menuItemResponse.setName(rs.getString("name"));
                    menuItemResponse.setImage(rs.getString("image"));
                    menuItemResponse.setPrice(rs.getBigDecimal("price"));
                    menuItemResponse.setDescription(rs.getString("description"));
                    menuItemResponse.setVegatarian(rs.getBoolean("is_vegatarian"));
                    menuItemResponse.setRestaurantName(rs.getString("restaurant_name"));
                    menuItemResponse.setSubCategoryName(rs.getString("sub_category_name"));
                    return menuItemResponse;
                }
        );
    }

    public List<MenuItemResponse> getAll(String ascOrDesc) {
        if (ascOrDesc.equalsIgnoreCase("asc")){
            String sql = "SELECT mi.id, mi.name, mi.image, mi.price, mi.description, mi.is_vegatarian, r.name AS restaurant_name, sc.name AS sub_category_name " +
                    "FROM menu_items mi " +
                    "JOIN restaurants r ON r.id = mi.restaurant_id " +
                    "JOIN sub_categories sc ON sc.id = mi.sub_category_id " +
                    "ORDER BY mi.price ASC, mi.is_vegatarian ASC";

            return jdbcTemplate.query(sql, new RowMapper<MenuItemResponse>() {
                @Override
                public MenuItemResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                    MenuItemResponse menuItemResponse = new MenuItemResponse();
                    menuItemResponse.setId(rs.getLong("id"));
                    menuItemResponse.setName(rs.getString("name"));
                    menuItemResponse.setImage(rs.getString("image"));
                    menuItemResponse.setPrice(rs.getBigDecimal("price"));
                    menuItemResponse.setDescription(rs.getString("description"));
                    menuItemResponse.setVegatarian(rs.getBoolean("is_vegatarian"));
                    menuItemResponse.setRestaurantName(rs.getString("restaurant_name"));
                    menuItemResponse.setSubCategoryName(rs.getString("sub_category_name"));
                    return menuItemResponse;
                }
            });

        }
        if (ascOrDesc.equalsIgnoreCase("desc")){
            String sql = "SELECT mi.id, mi.name, mi.image, mi.price, mi.description, mi.is_vegatarian, r.name AS restaurant_name, sc.name AS sub_category_name " +
                    "FROM menu_items mi " +
                    "JOIN restaurants r ON r.id = mi.restaurant_id " +
                    "JOIN sub_categories sc ON sc.id = mi.sub_category_id " +
                    "ORDER BY mi.price DESC, mi.is_vegatarian DESC";

            return jdbcTemplate.query(sql, new RowMapper<MenuItemResponse>() {
                @Override
                public MenuItemResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                    MenuItemResponse menuItemResponse = new MenuItemResponse();
                    menuItemResponse.setId(rs.getLong("id"));
                    menuItemResponse.setName(rs.getString("name"));
                    menuItemResponse.setImage(rs.getString("image"));
                    menuItemResponse.setPrice(rs.getBigDecimal("price"));
                    menuItemResponse.setDescription(rs.getString("description"));
                    menuItemResponse.setVegatarian(rs.getBoolean("is_vegatarian"));
                    menuItemResponse.setRestaurantName(rs.getString("restaurant_name"));
                    menuItemResponse.setSubCategoryName(rs.getString("sub_category_name"));
                    return menuItemResponse;
                }
            });

        }
        if (ascOrDesc.isBlank()){
            String sql = "SELECT mi.id, mi.name, mi.image, mi.price, mi.description, mi.is_vegatarian, r.name AS restaurant_name, sc.name AS sub_category_name " +
                    "FROM menu_items mi " +
                    "JOIN restaurants r ON r.id = mi.restaurant_id " +
                    "JOIN sub_categories sc ON sc.id = mi.sub_category_id ";

            return jdbcTemplate.query(sql, new RowMapper<MenuItemResponse>() {
                @Override
                public MenuItemResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                    MenuItemResponse menuItemResponse = new MenuItemResponse();
                    menuItemResponse.setId(rs.getLong("id"));
                    menuItemResponse.setName(rs.getString("name"));
                    menuItemResponse.setImage(rs.getString("image"));
                    menuItemResponse.setPrice(rs.getBigDecimal("price"));
                    menuItemResponse.setDescription(rs.getString("description"));
                    menuItemResponse.setVegatarian(rs.getBoolean("is_vegatarian"));
                    menuItemResponse.setRestaurantName(rs.getString("restaurant_name"));
                    menuItemResponse.setSubCategoryName(rs.getString("sub_category_name"));
                    return menuItemResponse;
                }
            });

        }
       return null;
    }



}
