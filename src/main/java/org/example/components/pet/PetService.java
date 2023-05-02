package org.example.components.pet;


import org.example.components.kind.KindRepository;
import org.example.components.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;

    private final ShopRepository shopRepository;
    private final KindRepository kindRepository;
    @Autowired
    public PetService(PetRepository petRepository, ShopRepository shopRepository,
                      KindRepository kindRepository) {
        this.petRepository = petRepository;
        this.shopRepository = shopRepository;
        this.kindRepository = kindRepository;
    }

    public List<Pet> findAll(){
        return petRepository.findAll();
    }
    public Pet findById(Long id){
        return petRepository.findById(id).get();
    }
    public boolean checkShop(Long id){
        return shopRepository.getById(id) == null;
    }

    public boolean checkKind(Long id ){
        return !kindRepository.findById(id).isPresent();
    }

    public void save(Pet pet, Long shop_id, Long kind_id){
        pet.setShop(shopRepository.getById(shop_id));
        pet.setKind(kindRepository.getById(kind_id));
        petRepository.save(pet);
    }
    public void deleteById(Long id){
        petRepository.deleteById(id);
    }
    public void update(Pet pet, Long id, Long shop_id, Long kind_id){

        Optional<Pet> pet1 = petRepository.findById(id);
        if (shop_id != null){
            pet.setShop(shopRepository.getById(shop_id));
        }else{
            pet.setShop(pet1.get().getShop());
        }
        if (kind_id != null){
            pet.setKind(kindRepository.getById(kind_id));
        }else{
            pet.setKind(pet1.get().getKind());
        }
        pet.setId(id);
        petRepository.save(pet);
    }


}
