package peaksoft.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoCategory.CategoryRequest;
import peaksoft.dto.dtoCategory.CategoryResponse;
import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;
import peaksoft.exception.AccessDeniedException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.template.CategoryTemplate;
import peaksoft.service.CategoryService;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryTemplate categoryResponses;
    @Override
    public SimpleResponse save(CategoryRequest categoryRequest) {
        try {
            Category category = Category.builder()
                    .name(categoryRequest.getName())
                    .build();
            categoryRepository.save(category);
             return new SimpleResponse(HttpStatus.OK, "Category with id: " + category.getId() + " successfully saved !");

        }catch (AccessDeniedException a){
           throw a;
        }
    }

    @Override
    public List<CategoryResponse> getAll() {
        try {
            List<Category> categories = categoryRepository.findAll();
            categories.forEach(category -> {
                if (category == null){
                    throw new NotFoundException("Categories is null ! ");
                }
            });
            return categoryResponses.getCategoryResponses();
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public CategoryResponse getById(Long id) {
        try {
            categoryRepository.findById(id).orElseThrow(()->
                    new NotFoundException("Category with id: " + id + " not found !"));
        return categoryResponses.getCategoryResponseById(id);
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public SimpleResponse update(Long id, CategoryRequest categoryRequest) {
        try {
            Category category = categoryRepository.findById(id).orElseThrow(() ->
                    new NotFoundException("Category with id: " + id + " not found !"));
            category.setName(categoryRequest.getName());
            categoryRepository.save(category);
            return new SimpleResponse(HttpStatus.OK, "Category with id: " + id + " successfully updated !");
        } catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public SimpleResponse delete(Long id) {
        try {
            Category category = categoryRepository.findById(id).orElseThrow(() ->
                    new NotFoundException("Category with id: " + id + " not found !"));
            List<SubCategory> subCategories = category.getSubCategories();
            for (SubCategory subCategory : subCategories) {
                category.setSubCategories(null);
                subCategory.setCategory(null);
            }
            categoryRepository.delete(category);
            return new SimpleResponse(HttpStatus.OK, "Category with id: " + id + " successfully deleted !");
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }
}
