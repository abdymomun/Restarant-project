package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.dtoCategory.CategoryResponse;
import peaksoft.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}