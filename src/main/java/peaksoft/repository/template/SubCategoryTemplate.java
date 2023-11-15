package peaksoft.repository.template;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import peaksoft.dto.dtoSubCategory.SubCategoryResponse;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class SubCategoryTemplate {
    public final JdbcTemplate jdbcTemplate;

    public List<SubCategoryResponse> getAll() {
        String sql = "SELECT s.id, s.name, c.name AS category_name " +
                "FROM sub_categories s " +
                "JOIN categories c ON s.category_id = c.id group by c.name, s.name, s.id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long subCategoryId = rs.getLong("id");
            String subCategoryName = rs.getString("name");
            String categoryName = rs.getString("category_name");


            return new SubCategoryResponse(subCategoryId, subCategoryName, categoryName);
        });
    }

    public SubCategoryResponse getById(Long subCategoryId) {
        String sql = "SELECT s.id, s.name, c.name AS category_name " +
                "FROM sub_categories s " +
                "JOIN categories c ON s.category_id = c.id " +
                "WHERE s.id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{subCategoryId}, (rs, rowNum) -> {
            String subCategoryName = rs.getString("name");
            String categoryName = rs.getString("category_name");

            return new SubCategoryResponse(subCategoryId, subCategoryName, categoryName);
        });

    }

}
