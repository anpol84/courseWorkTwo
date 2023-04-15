package org.example.components.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@Controller
@RestController
@RequestMapping("/api/v1/users/items")
public class ItemController {
    private final ItemService itemService;
    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public Iterable<Item> findAll(){
        return itemService.findAll();
    }

    @PostMapping
    public void save(@RequestBody Item item){
        itemService.save(item);
    }

    @DeleteMapping
    public void deleteById(@RequestParam Long id){
        itemService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Optional<Item> findById(@PathVariable Long id){
        return itemService.findById(id);
    }

    @GetMapping("/search")
    public Iterable<Item> filter(@RequestParam(name = "category", required = false) String category,
                                 @RequestParam(name = "pet", required = false) String pet,
                                 @RequestParam(name = "purchasePrice", required = false) Double purchasePrice,
                                 @RequestParam(name = "sellingPrice", required = false) Double sellingPrice){
        return itemService.filter(category, pet, purchasePrice, sellingPrice);
    }
}
