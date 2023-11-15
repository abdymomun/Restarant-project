package peaksoft.repository.template;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import peaksoft.dto.dtoCheque.ChequeResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class ChequeTemplate {
    private final JdbcTemplate jdbcTemplate;

    public List<ChequeResponse> getAll() {
        String sql = "SELECT r.name as restaurantName, CONCAT(u.first_name, ' ', u.last_name) as waiterFullName , mi.name, c.price_average, r.service as service,  (SUM(mi.price) + r.service + c.price_average) as total_price " +
                "FROM cheques c" +
                "         JOIN users u ON u.id = c.user_id" +
                "         JOIN menu_items_cheques mic ON c.id = mic.cheques_id " +
                "         JOIN menu_items mi ON mi.id = mic.menu_items_id " +
                "         JOIN restaurants r ON r.id = mi.restaurant_id " +
                "GROUP BY r.name, c.price_average, mi.name, r.service, waiterFullName;";

        return jdbcTemplate.query(sql, new RowMapper<ChequeResponse>() {
            @Override
            public ChequeResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                ChequeResponse chequeResponse = new ChequeResponse();
                chequeResponse.setRestaurantName(rs.getString("restaurantName"));
                chequeResponse.setPriceAverage(rs.getBigDecimal("price_average"));
                chequeResponse.setWaiterFullName(rs.getString("waiterFullName"));
                chequeResponse.setGrandTotal(rs.getBigDecimal("total_price"));
                chequeResponse.setService(rs.getInt("service"));
                String menuNameString = rs.getString("name");
                List<String> menuNameList = Arrays.asList(menuNameString.split(","));
                chequeResponse.setMenuName(menuNameList);
                return chequeResponse;
            }
        });
    }

    public ChequeResponse getById(Long id) {
        String sql = "SELECT c.id, r.name as restaurantName, CONCAT(u.first_name, ' ', u.last_name) as waiterFullName , mi.name, c.price_average, r.service as service,  (SUM(mi.price) + r.service + c.price_average) as total_price " +
                "FROM cheques c" +
                "         JOIN users u ON u.id = c.user_id" +
                "         JOIN menu_items_cheques mic ON c.id = mic.cheques_id " +
                "         JOIN menu_items mi ON mi.id = mic.menu_items_id " +
                "         JOIN restaurants r ON r.id = mi.restaurant_id  where c.id =? " +
                "GROUP BY c.id, r.name, c.price_average, mi.name, r.service, waiterFullName ;";



        List<ChequeResponse> results = jdbcTemplate.query(sql, new Object[]{id}, new RowMapper<ChequeResponse>() {
            @Override
            public ChequeResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                ChequeResponse chequeResponse = new ChequeResponse();
                chequeResponse.setId(rs.getLong("id"));
                chequeResponse.setRestaurantName(rs.getString("restaurantName"));
                chequeResponse.setPriceAverage(rs.getBigDecimal("price_average"));
                chequeResponse.setWaiterFullName(rs.getString("waiterFullName"));
                chequeResponse.setGrandTotal(rs.getBigDecimal("total_price"));
                chequeResponse.setService(rs.getInt("service"));
                String menuNameString = rs.getString("name");
                List<String> menuNameList = Arrays.asList(menuNameString.split(","));
                chequeResponse.setMenuName(menuNameList);
                return chequeResponse;
            }
        });

        if (results.isEmpty()) {
            // Вернуть пустой объект или бросить исключение, если чек не найден
            return new ChequeResponse(); // или бросить исключение
        } else {
            // Вернуть первый результат, так как ожидается, что id уникальный
            return results.get(0);
        }
    }



}
