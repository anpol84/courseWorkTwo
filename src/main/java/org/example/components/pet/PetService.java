package org.example.components.pet;

import org.example.auth.UserService;
import org.example.components.item.Item;
import org.example.components.item.ItemDto;
import org.example.components.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final MyPetRepository myPetRepository;
    private final UserService userService;
    private final ShopRepository shopRepository;
    @Autowired
    public PetService(PetRepository petRepository, MyPetRepository myPetRepository,
                      UserService userService, ShopRepository shopRepository) {
        this.petRepository = petRepository;
        this.myPetRepository = myPetRepository;
        this.userService = userService;
        this.shopRepository = shopRepository;
    }

    public ResponseEntity<String> findAll(){
        Iterable<Pet> pets = petRepository.findAll();
        List<PetDto> pets2 = new ArrayList<>();
        for (Pet pet : pets){
            pets2.add(PetDto.fromPet(pet));
        }
        return ResponseEntity.ok(pets2.toString());
    }

    public ResponseEntity<String> save(Pet pet, HttpServletRequest request, Long shop_id){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        pet.setShop(shopRepository.getById(shop_id));
        petRepository.save(pet);
        return ResponseEntity.ok("Saved");
    }

    public ResponseEntity<String> deleteById(Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        petRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    public ResponseEntity<String> findById(Long id){
        Optional<Pet> pet = petRepository.findById(id);
        if (!pet.isPresent()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }else{
            return ResponseEntity.ok(PetDto.fromPet(pet.get()).toString());
        }
    }

    public ResponseEntity<String> filter(String kind, Double weight, String alias, String gender, String color,
                                         Double price){
        Iterable<Pet> pets =  myPetRepository.filter(kind, weight, alias, gender, color, price);
        List<PetDto> pets2 = new ArrayList<>();
        for (Pet pet : pets){
            pets2.add(PetDto.fromPet(pet));
        }
        return ResponseEntity.ok(pets2.toString());
    }
}
