package peaksoft.repository.template;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import peaksoft.dto.dtoCategory.CategoryResponse;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CategoryTemplate {
    private final JdbcTemplate jdbcTemplate;


    public List<CategoryResponse> getCategoryResponses() {
        String sql = "SELECT c.id, c.name, STRING_AGG(s.name, ',') AS subCategoryNames " +
                "FROM categories c " +
                "LEFT JOIN sub_categories s ON c.id = s.category_id " +
                "GROUP BY c.id, c.name ORDER BY STRING_AGG(s.name, ',') ";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new CategoryResponse(
                        rs.getLong("id"),
                        rs.getString("name"),
                        Arrays.asList(rs.getString("subCategoryNames").split(","))
                )
        );
    }

    public CategoryResponse getCategoryResponseById(Long id) {
        String sql = "SELECT c.id, c.name, STRING_AGG(s.name, ',') AS subCategoryNames " +
                "FROM categories c " +
                "JOIN sub_categories s ON c.id = s.category_id " +
                "WHERE c.id = ? " +
                "GROUP BY c.id, c.name ORDER BY STRING_AGG(s.name, ',')";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (rs, rowNum) -> {
                    String subCategoryNamesString = rs.getString("subCategoryNames");
                    List<String> subCategoryNames = Arrays.asList(subCategoryNamesString.split(","));
                    return new CategoryResponse(
                            rs.getLong("id"),
                            rs.getString("name"),
                            subCategoryNames
                    );
                }
        );
    }

}
