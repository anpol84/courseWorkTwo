package org.example.components.item;


import org.example.components.empolyee.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/items")
public class ItemController {
    private final ItemService itemService;
    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    @GetMapping()
    public String findAll(Model model){
        List<Item> items =  itemService.findAll();
        model.addAttribute("items", items);
        return "item";
    }
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        model.addAttribute("item", itemService.findById(id));
        return "item_info";
    }

    @GetMapping("/update")
    public String updatePage(@ModelAttribute Item item, @RequestParam Long id,
                             @RequestParam Long shop_id, @RequestParam Long kind_id,
                             @RequestParam Long category_id, Model model){
        model.addAttribute("id", id);
        model.addAttribute("item", item);
        model.addAttribute("shop_id", shop_id);
        model.addAttribute("kind_id", kind_id);
        model.addAttribute("category_id", category_id);
        return "item_put";
    }


    @PostMapping
    public String save(@ModelAttribute Item item, Model model,
                                       @RequestParam(name = "shop_id") Long shop_id,
                                       @RequestParam(name = "kind_id") Long kind_id,
                                       @RequestParam(name = "category_id") Long category_id){
        if (itemService.checkKind(kind_id)){
            model.addAttribute("message", "No Such Kind");
            return findAll(model);
        }
        if (itemService.checkShop(shop_id)){
            model.addAttribute("message", "No Such Shop");
            return findAll(model);
        }
        if (itemService.checkCategory(category_id)){
            model.addAttribute("message", "No Such Category");
            return findAll(model);
        }
        itemService.save(item, shop_id, kind_id, category_id);
        return findAll(model);
    }

    @DeleteMapping
    public String deleteById(@RequestParam Long id, Model model){
        itemService.deleteById(id);
        return findAll(model);
    }

    @PutMapping
    public String update(@ModelAttribute Item item, @RequestParam Long id,
                                         @RequestParam(required = false) Long shop_id,
                                         @RequestParam(required = false) Long category_id,
                                         @RequestParam(required = false) Long kind_id,
                                         Model model){
        if (kind_id != null && itemService.checkKind(kind_id)){
            model.addAttribute("message", "No Such Kind");
            return findAll(model);
        }
        if (shop_id != null && itemService.checkShop(shop_id)){
            model.addAttribute("message", "No Such Shop");
            return findAll(model);
        }
        if ( category_id != null && itemService.checkCategory(category_id)){
            model.addAttribute("message", "No Such Category");
            return findAll(model);
        }
        itemService.update(item, id, shop_id, category_id, kind_id);
        return findAll(model);
    }


}
