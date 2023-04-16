package org.example.components.pet;

import org.example.auth.UserService;
import org.example.components.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final MyPetRepository myPetRepository;
    private final UserService userService;
    @Autowired
    public PetService(PetRepository petRepository, MyPetRepository myPetRepository, UserService userService) {
        this.petRepository = petRepository;
        this.myPetRepository = myPetRepository;
        this.userService = userService;
    }

    public ResponseEntity<String> findAll(){
        Iterable<Pet> pets =petRepository.findAll();
        return ResponseEntity.ok(pets.toString());
    }

    public ResponseEntity<String> save(Pet pet, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
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
            return ResponseEntity.ok(pet.toString());
        }
    }

    public ResponseEntity<String> filter(String kind, Double weight, String alias, String gender, String color,
                                         Double price){
        Iterable<Pet> pets =  myPetRepository.filter(kind, weight, alias, gender, color, price);
        return ResponseEntity.ok(pets.toString());
    }
}
