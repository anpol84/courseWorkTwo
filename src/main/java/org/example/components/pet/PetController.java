package org.example.components.pet;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }
    @GetMapping
    public Iterable<Pet> findAll(){
        return petService.findAll();
    }

    @PostMapping
    public void save(@RequestBody Pet pet){
        petService.save(pet);
    }

    @DeleteMapping
    public void deleteById(@RequestParam Long id){
        petService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Optional<Pet> findById(@PathVariable Long id){
        return petService.findById(id);
    }

    @GetMapping("/search")
    public Iterable<Pet> filter(@RequestParam(name = "kind", required = false) String kind,
                                @RequestParam(name = "weight", required = false) Double weight,
                                @RequestParam(name = "alias", required = false) String alias,
                                @RequestParam(name = "gender", required = false) String gender,
                                @RequestParam(name = "color", required = false) String color,
                                @RequestParam(name = "price", required = false) Double price){
        return petService.filter(kind, weight, alias, gender, color, price);
    }
}
