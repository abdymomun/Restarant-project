package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoCategory.CategoryRequest;
import peaksoft.dto.dtoCategory.CategoryResponse;

import java.util.List;


public interface CategoryService {
    SimpleResponse save(CategoryRequest categoryRequest);
    List<CategoryResponse> getAll();
    CategoryResponse getById(Long id);
    SimpleResponse update(Long id,CategoryRequest categoryRequest);
    SimpleResponse delete(Long id);
}
