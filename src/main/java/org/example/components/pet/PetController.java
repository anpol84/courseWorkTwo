package org.example.components.pet;

import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("")
    public ResponseEntity<String> filter(@RequestParam(name = "weight", required = false) Double weight,
                                         @RequestParam(name = "alias", required = false) String alias,
                                         @RequestParam(name = "gender", required = false) String gender,
                                         @RequestParam(name = "color", required = false) String color,
                                         @RequestParam(name = "price", required = false) Double price){
        if  (weight != null || alias != null || gender != null || color != null || price != null){
            return petService.filter(weight, alias, gender, color, price);
        }
        return petService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable Long id){
        return petService.findById(id);
    }



    @PostMapping
    public ResponseEntity<String> save(@RequestBody Pet pet, @NonNull HttpServletRequest request,
                                       @RequestParam Long shop_id, @RequestParam Long kind_id){
        return petService.save(pet, request, shop_id, kind_id);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteById(@RequestParam Long id, @NonNull HttpServletRequest request){
        return petService.deleteById(id, request);
    }


}
