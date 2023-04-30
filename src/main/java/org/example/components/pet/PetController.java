package org.example.components.pet;


import org.example.components.item.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
@RequestMapping("/api/v1/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("")
    public String findAll(Model model){
        List<Pet> pets = petService.findAll();
        model.addAttribute("pets", pets);
        return "pet";
    }
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        model.addAttribute("pet", petService.findById(id));
        return "pet_info";
    }

    @GetMapping("/update")
    public String updatePage(@ModelAttribute Pet pet, @RequestParam Long id,
                             @RequestParam Long shop_id, @RequestParam Long kind_id, Model model){
        model.addAttribute("id", id);
        model.addAttribute("pet", pet);
        model.addAttribute("shop_id", shop_id);
        model.addAttribute("kind_id", kind_id);
        return "pet_put";
    }


    @PostMapping
    public String save(@ModelAttribute Pet pet, Model model,
                                       @RequestParam Long shop_id, @RequestParam Long kind_id){
        if (petService.checkKind(kind_id)){
            model.addAttribute("message", "No Such Kind");
            return findAll(model);
        }
        if (petService.checkShop(shop_id)){
            model.addAttribute("message", "No Such Shop");
            return findAll(model);
        }
        petService.save(pet, shop_id, kind_id);
        return findAll(model);

    }

    @DeleteMapping
    public String deleteById(@RequestParam Long id, Model model){
        petService.deleteById(id);
        return findAll(model);
    }

    @PutMapping
    public String update(@ModelAttribute Pet pet, @RequestParam Long id,
                                         @RequestParam(required = false) Long shop_id,
                                         @RequestParam(required = false) Long kind_id,
                                        Model model){
        if (kind_id != null && petService.checkKind(kind_id)){
            model.addAttribute("message", "No Such Kind");
            return findAll(model);
        }
        if (shop_id != null && petService.checkShop(shop_id)){
            model.addAttribute("message", "No Such Shop");
            return findAll(model);
        }
        model.addAttribute("id", id);
        petService.update(pet, id, shop_id, kind_id);
        return findAll(model);
    }

}
