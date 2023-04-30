package org.example.components.shop;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShopService {
    private final ShopRepository shopRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public void save(Shop shop){

        shopRepository.save(shop);
    }

    public List<?> findAll(){

        Iterable<Shop> shops = shopRepository.findAll();
        List<ShopDto> shops2 = new ArrayList<>();
        for (Shop shop : shops){
            shops2.add(ShopDto.fromShop(shop));
        }
        return shops2;
    }
    @Transactional
    public void deleteById(Long id){
        System.out.println(id);
        shopRepository.deleteById(id);
    }

    public Shop findById(Long id){
        Optional<Shop> shop = shopRepository.findById(id);
        return shop.get();
    }



    public void update(Shop shop, Long id){

        Optional<Shop> shop1 = shopRepository.findById(id);

        shop.setPets(shop1.get().getPets());
        shop.setAddress(shop1.get().getAddress());
        shop.setEmployees(shop1.get().getEmployees());
        shop.setItems(shop1.get().getItems());
        shop.setId(id);
        shopRepository.save(shop);
    }
}
