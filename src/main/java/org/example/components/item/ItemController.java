package org.example.components.item;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
@Controller
@RestController
@RequestMapping("/api/v1/items")
public class ItemController {
    private final ItemService itemService;
    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    @GetMapping()
    public ResponseEntity<String> filter(@RequestParam(name = "category", required = false) String category,
                                         @RequestParam(name = "pet", required = false) String pet,
                                         @RequestParam(name = "purchasePrice", required = false) Double purchasePrice,
                                         @RequestParam(name = "sellingPrice", required = false) Double sellingPrice){
        if (category != null || pet != null || purchasePrice != null || sellingPrice != null) {
            return itemService.filter(category, pet, purchasePrice, sellingPrice);
        }else{
            return itemService.findAll();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable Long id){
        return itemService.findById(id);
    }



    @PostMapping
    public ResponseEntity<String> save(@RequestBody Item item, @NonNull HttpServletRequest request,
                                       @RequestParam Long shop_id){
        return itemService.save(item, request, shop_id);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteById(@RequestParam Long id, @NonNull HttpServletRequest request){
        return itemService.deleteById(id, request);
    }


}
