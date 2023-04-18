package org.example.components.category;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name = "name", required = false) String name,
                                          @RequestParam(name = "purpose", required = false) String purpose,
                                          @RequestParam(name = "averageSize", required = false) String averageSize){
        if (name != null || purpose != null || averageSize != null){
            return categoryService.filter(name, purpose, averageSize);
        }
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return categoryService.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Category category, @NonNull HttpServletRequest request){
        return categoryService.save(category, request);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteById(@RequestParam Long id, @NonNull HttpServletRequest request){
        return categoryService.deleteById(id, request);
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Category category, @RequestParam Long id,
                                         @NonNull HttpServletRequest request){
        return categoryService.update(category, id, request);
    }
}
