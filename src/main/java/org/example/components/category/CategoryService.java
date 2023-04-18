package org.example.components.category;

import org.example.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final MyCategoryRepository myCategoryRepository;
    private final UserService userService;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, MyCategoryRepository myCategoryRepository,
                           UserService userService) {
        this.categoryRepository = categoryRepository;
        this.myCategoryRepository = myCategoryRepository;
        this.userService = userService;
    }

    public ResponseEntity<?> findAll(){
        Iterable<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categories2 = new ArrayList<>();
        for (Category category : categories){
            categories2.add(CategoryDto.fromCategory(category));
        }
        return new ResponseEntity<>(categories2, HttpStatus.OK);
    }

    public ResponseEntity<?> findById(Long id){
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()){
            return new ResponseEntity<>("No such entity", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category.get(), HttpStatus.OK);
    }

    public ResponseEntity<?> filter(String name, String purpose, String averageSize){
        Iterable<Category> categories = myCategoryRepository.filter(name, purpose, averageSize);
        List<CategoryDto> categories2 = new ArrayList<>();
        for (Category category : categories){
            categories2.add(CategoryDto.fromCategory(category));
        }
        return new ResponseEntity<>(categories2, HttpStatus.OK);
    }

    public ResponseEntity<String> save(Category category, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        categoryRepository.save(category);
        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteById(Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        if (categoryRepository.getById(id) == null){
            return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
        }
        categoryRepository.deleteById(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    public ResponseEntity<String> update(Category category, Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Category category1 = categoryRepository.getById(id);
        if (category1 == null){
            return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
        }
        category.setId(id);
        category.setItems(category1.getItems());
        categoryRepository.save(category);
        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }
}
