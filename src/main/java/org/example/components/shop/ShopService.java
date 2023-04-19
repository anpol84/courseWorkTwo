package org.example.components.shop;

import org.example.auth.UserService;
import org.example.components.address.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShopService {
    private final ShopRepository shopRepository;
    private final UserService userService;
    private final MyShopRepository myShopRepository;

    private final AddressRepository addressRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository, UserService userService,
                       MyShopRepository myShopRepository, AddressRepository addressRepository) {
        this.shopRepository = shopRepository;
        this.userService = userService;
        this.myShopRepository = myShopRepository;
        this.addressRepository = addressRepository;
    }

    public ResponseEntity<String> save(Shop shop, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        shopRepository.save(shop);
        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }

    public ResponseEntity<?> findAll(HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            Iterable<Shop> shops = shopRepository.findAll();
            List<ShopUsersDto> shops2 = new ArrayList<>();
            for (Shop shop : shops){
                shops2.add(ShopUsersDto.fromShop(shop));
            }
            return new ResponseEntity<>(shops2, HttpStatus.OK);
        }
        Iterable<Shop> shops = shopRepository.findAll();
        List<ShopDto> shops2 = new ArrayList<>();
        for (Shop shop : shops){
            shops2.add(ShopDto.fromShop(shop));
        }
        return new ResponseEntity<>(shops2, HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<String> deleteById(Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        if (!shopRepository.findById(id).isPresent()) return new ResponseEntity<>("No such entity", HttpStatus.NOT_FOUND);
        shopRepository.deleteById(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    public ResponseEntity<?> findById(Long id, HttpServletRequest request){
        Optional<Shop> shop = shopRepository.findById(id);
        if (!shop.isPresent()){
            return new ResponseEntity<>("No such entity", HttpStatus.NOT_FOUND);
        }else{
            if (!userService.checkAdmin(request)) {
                return ResponseEntity.ok(ShopUsersDto.fromShop(shop.get()));
            }
            return ResponseEntity.ok(ShopDto.fromShop(shop.get()));
        }
    }

    public ResponseEntity<?> filter(String head, String phone, HttpServletRequest request){
        Iterable<Shop> shops = myShopRepository.filter(head, phone);
        if (!userService.checkAdmin(request)) {
            List<ShopUsersDto> shops2 = new ArrayList<>();
            for (Shop shop : shops){
                shops2.add(ShopUsersDto.fromShop(shop));
            }
            return new ResponseEntity<>(shops2, HttpStatus.OK);
        }
        List<ShopDto> shops2 = new ArrayList<>();
        for (Shop shop : shops){
            shops2.add(ShopDto.fromShop(shop));
        }
        return ResponseEntity.ok(shops2);
    }

    public ResponseEntity<String> update(Shop shop, Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Optional<Shop> shop1 = shopRepository.findById(id);
        if (!shop1.isPresent()){
            return new ResponseEntity<>("No such entity", HttpStatus.NOT_FOUND);
        }
        shop.setPets(shop1.get().getPets());
        shop.setAddress(shop1.get().getAddress());
        shop.setEmployees(shop1.get().getEmployees());
        shop.setItems(shop1.get().getItems());
        shop.setId(id);
        shopRepository.save(shop);
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }
}
