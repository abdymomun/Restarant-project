package peaksoft.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoSubCategory.SubCategoryRequest;
import peaksoft.dto.dtoSubCategory.SubCategoryResponse;
import peaksoft.entity.Category;
import peaksoft.entity.MenuItem;
import peaksoft.entity.SubCategory;
import peaksoft.exception.AccessDeniedException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.repository.template.SubCategoryTemplate;
import peaksoft.service.SubCategoryService;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryTemplate subCategoryTemplate;
    @Override
    public SimpleResponse save(SubCategoryRequest subCategoryRequest, Long categoryId) {
        try {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                    new NotFoundException("Category with id: " + categoryId + " not found ! "));
            SubCategory subCategory = SubCategory.builder()
                    .name(subCategoryRequest.getName())
                    .category(category)
                    .build();
            subCategory.setCategory(category);
            category.setSubCategories(List.of(subCategory));
            subCategoryRepository.save(subCategory);
            return new SimpleResponse(HttpStatus.OK, "SubCategory with id: " + category.getId() + " successfully saved !");
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public List<SubCategoryResponse> getAll() {
        try {
            return subCategoryTemplate.getAll();
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public SubCategoryResponse getById(Long id) {
        try {
            return subCategoryTemplate.getById(id);
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }
    @Override
    public SimpleResponse update(Long id, SubCategoryRequest subCategoryRequest) {
        try {
            SubCategory subCategory = subCategoryRepository.findById(id).orElseThrow(() ->
                    new NotFoundException("SubCategory with id: " + id + " not found !"));
            subCategory.setName(subCategoryRequest.getName());
            subCategoryRepository.save(subCategory);
            return new SimpleResponse(HttpStatus.OK, "Sub Category with id: " + id + " successfully updated !!");
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public SimpleResponse delete(Long id) {
        try {
            SubCategory subCategory = subCategoryRepository.findById(id).orElseThrow(() ->
                    new NotFoundException("SubCategory with id: " + id + " not found !"));
            List<MenuItem> menuItem = subCategory.getMenuItem();
            for (MenuItem item : menuItem) {
                item.setSubCategory(null);
                subCategory.setMenuItem(null);
                subCategory.setCategory(null);
            }
            subCategoryRepository.delete(subCategory);
            return new SimpleResponse(HttpStatus.OK, "Sub Category with id: " + id + " successfully deleted ! !");

        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }

    }
}
