package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoSubCategory.SubCategoryRequest;
import peaksoft.dto.dtoSubCategory.SubCategoryResponse;

import java.util.List;

public interface SubCategoryService {
    SimpleResponse save(SubCategoryRequest subCategoryRequest,Long categoryId);
    List<SubCategoryResponse>getAll();
    SubCategoryResponse getById(Long id);

    SimpleResponse update(Long id,SubCategoryRequest subCategoryRequest);

    SimpleResponse delete(Long id);
}
