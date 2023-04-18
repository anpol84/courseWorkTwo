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
    public ResponseEntity<?> filter(@RequestParam(name = "category", required = false) String category,
                                         @RequestParam(name = "purchasePrice", required = false) Double purchasePrice,
                                         @RequestParam(name = "sellingPrice", required = false) Double sellingPrice){
        if (category != null  || purchasePrice != null || sellingPrice != null) {
            return itemService.filter(category, purchasePrice, sellingPrice);
        }else{
            return itemService.findAll();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return itemService.findById(id);
    }



    @PostMapping
    public ResponseEntity<String> save(@RequestBody Item item, @NonNull HttpServletRequest request,
                                       @RequestParam(name = "shop_id") Long shop_id,
                                       @RequestParam(name = "kind_id") Long kind_id,
                                       @RequestParam(name = "category_id") Long category_id){
        return itemService.save(item, request, shop_id, kind_id, category_id);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteById(@RequestParam Long id, @NonNull HttpServletRequest request){
        return itemService.deleteById(id, request);
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Item item, @RequestParam Long id,
                                         @RequestParam(required = false) Long shop_id,
                                         @RequestParam(required = false) Long category_id,
                                         @RequestParam(required = false) Long kind_id,
                                         @NonNull HttpServletRequest request){
        return itemService.update(item, id, shop_id, category_id, kind_id, request);
    }


}
