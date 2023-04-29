package org.example.components.shop;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/api/v1/shops")
public class ShopController {
    private final ShopService shopService;
    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping
    public String getShops(Model model){
        List<Shop> shops = (List<Shop>) shopService.findAll();
        model.addAttribute("shops", shops);
        return "shop";
    }
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        Shop shop = shopService.findById(id);
        model.addAttribute("shop", ShopDto.fromShop(shop));
        return "shop_info";
    }

    @PostMapping
    public String save(@ModelAttribute Shop shop, Model model){
       shopService.save(shop);
       return getShops(model);
    }
    @DeleteMapping
    public String deleteById(@RequestParam Long id, Model model){
        shopService.deleteById(id);
        return getShops(model);
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Shop shop, @RequestParam Long id,
                                         @NonNull HttpServletRequest request){
        return shopService.update(shop, id, request);
    }
}
