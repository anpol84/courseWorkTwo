package org.example.components.shop;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/shops")
public class ShopController {
    private final ShopService shopService;
    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping
    public ResponseEntity<String> getShops(@RequestParam(name = "address", required = false) String address,
                                           @RequestParam(name = "phone", required = false) String phone,
                                           @NonNull HttpServletRequest request){
        if (phone != null || address != null){
            return shopService.filter(address, phone);
        }
        return shopService.findAll(request);
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@RequestParam Long id, @NonNull HttpServletRequest request){
        return shopService.findById(id, request);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Shop shop , @NonNull HttpServletRequest request){
       return shopService.save(shop, request);

    }
    @DeleteMapping
    public ResponseEntity<String> deleteById(@RequestParam Long id, @NonNull HttpServletRequest request){
        return shopService.deleteById(id, request);
    }
}