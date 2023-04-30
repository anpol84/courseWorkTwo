package org.example.components.category;


import org.example.components.address.Address;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String findAll(Model model){
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "category";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        model.addAttribute("category", categoryService.findById(id));
        return "category_info";
    }

    @GetMapping("/update")
    public String updatePage(@RequestParam Long id, @ModelAttribute Category category, Model model){
        model.addAttribute("id", id);
        model.addAttribute("category", category);
        return "category_put";
    }

    @PostMapping
    public String save(@ModelAttribute Category category, Model model){
        categoryService.save(category);
        return findAll(model);
    }

    @DeleteMapping
    public String deleteById(@RequestParam Long id, Model model){
        categoryService.deleteById(id);
        return findAll(model);
    }

    @PutMapping
    public String update(@ModelAttribute Category category, @RequestParam Long id,
                                         Model model){
        categoryService.update(category, id);
        return findAll(model);
    }
}
