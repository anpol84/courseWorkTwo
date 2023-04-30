package org.example.components.shop;

import org.example.components.kind.Kind;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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
    @GetMapping("/update")
    public String updatePage(@ModelAttribute Shop shop, @RequestParam Long id, Model model){
        model.addAttribute("id", id);
        model.addAttribute("shop", shop);
        return "shop_put";
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
    public String update(@ModelAttribute Shop shop, @RequestParam Long id, Model model){
        shopService.update(shop, id);
        return getShops(model);
    }
}
