package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoSubCategory.SubCategoryRequest;
import peaksoft.dto.dtoSubCategory.SubCategoryResponse;
import peaksoft.service.SubCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    @Autowired
    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save/{categoryId}")
    public ResponseEntity<SimpleResponse> saveSubCategory(@RequestBody SubCategoryRequest subCategoryRequest, @PathVariable Long categoryId) {
        SimpleResponse response = subCategoryService.save(subCategoryRequest, categoryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubCategoryResponse>> getAllSubCategories() {
        List<SubCategoryResponse> subCategories = subCategoryService.getAll();
        return ResponseEntity.ok(subCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubCategoryResponse> getSubCategoryById(@PathVariable Long id) {
        SubCategoryResponse subCategory = subCategoryService.getById(id);
        return ResponseEntity.ok(subCategory);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SimpleResponse> updateSubCategory(@PathVariable Long id, @RequestBody SubCategoryRequest subCategoryRequest) {
        SimpleResponse response = subCategoryService.update(id, subCategoryRequest);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<SimpleResponse> deleteSubCategory(@PathVariable Long id) {
        SimpleResponse response = subCategoryService.delete(id);
        return ResponseEntity.ok(response);
    }
}

