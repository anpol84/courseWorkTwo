package org.example.components.pet;

import org.example.auth.UserService;
import org.example.components.item.Item;
import org.example.components.item.ItemDto;
import org.example.components.kind.KindRepository;
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
    private final KindRepository kindRepository;
    @Autowired
    public PetService(PetRepository petRepository, MyPetRepository myPetRepository,
                      UserService userService, ShopRepository shopRepository,
                      KindRepository kindRepository) {
        this.petRepository = petRepository;
        this.myPetRepository = myPetRepository;
        this.userService = userService;
        this.shopRepository = shopRepository;
        this.kindRepository = kindRepository;
    }

    public ResponseEntity<?> findAll(){
        Iterable<Pet> pets = petRepository.findAll();
        List<PetDto> pets2 = new ArrayList<>();
        for (Pet pet : pets){
            pets2.add(PetDto.fromPet(pet));
        }
        return ResponseEntity.ok(pets2);
    }

    public ResponseEntity<String> save(Pet pet, HttpServletRequest request, Long shop_id, Long kind_id){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        if (shopRepository.getById(shop_id) == null){
            return new ResponseEntity<>("Shop not found", HttpStatus.NOT_FOUND);
        }
        pet.setShop(shopRepository.getById(shop_id));
        if (kindRepository.getById(kind_id) == null){
            return new ResponseEntity<>("Kind not found", HttpStatus.NOT_FOUND);
        }
        pet.setKind(kindRepository.getById(kind_id));
        petRepository.save(pet);
        return ResponseEntity.ok("Saved");
    }

    public ResponseEntity<String> deleteById(Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        if (petRepository.getById(id) == null){
            return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
        }
        petRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    public ResponseEntity<?> findById(Long id){
        Optional<Pet> pet = petRepository.findById(id);
        if (!pet.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity not found");
        }else{
            return ResponseEntity.ok(PetDto.fromPet(pet.get()));
        }
    }

    public ResponseEntity<?> filter(Double weight, String alias, String gender, String color,
                                         Double price){
        Iterable<Pet> pets =  myPetRepository.filter(weight, alias, gender, color, price);
        List<PetDto> pets2 = new ArrayList<>();
        for (Pet pet : pets){
            pets2.add(PetDto.fromPet(pet));
        }
        return ResponseEntity.ok(pets2);
    }

    public ResponseEntity<String> update(Pet pet, Long id, Long shop_id, Long kind_id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Pet pet1 = petRepository.getById(id);
        if (pet1 == null){
            return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
        }
        if (shop_id != null){
            pet.setShop(shopRepository.getById(shop_id));
        }else{
            pet.setShop(pet1.getShop());
        }
        if (kind_id != null){
            pet.setKind(kindRepository.getById(kind_id));
        }else{
            pet.setKind(pet1.getKind());
        }
        pet.setId(id);
        petRepository.save(pet);
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }
}
