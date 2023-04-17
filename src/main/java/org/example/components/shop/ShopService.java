package org.example.components.shop;

import org.example.auth.UserService;
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

    @Autowired
    public ShopService(ShopRepository shopRepository, UserService userService, MyShopRepository myShopRepository) {
        this.shopRepository = shopRepository;
        this.userService = userService;
        this.myShopRepository = myShopRepository;
    }

    public ResponseEntity<String> save(Shop shop, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        shopRepository.save(shop);
        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }

    public ResponseEntity<String> findAll(HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            Iterable<Shop> shops = shopRepository.findAll();
            List<ShopUsersDto> shops2 = new ArrayList<>();
            for (Shop shop : shops){
                shops2.add(ShopUsersDto.fromShop(shop));
            }
            return new ResponseEntity<>(shops2.toString(), HttpStatus.OK);
        }
        Iterable<Shop> shops = shopRepository.findAll();
        List<ShopDto> shops2 = new ArrayList<>();
        for (Shop shop : shops){
            shops2.add(ShopDto.fromShop(shop));
        }
        return new ResponseEntity<>(shops2.toString(), HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<String> deleteById(Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        shopRepository.deleteById(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    public ResponseEntity<String> findById(Long id, HttpServletRequest request){
        Optional<Shop> shop = shopRepository.findById(id);
        if (!shop.isPresent()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }else{
            if (!userService.checkAdmin(request)) {
                return ResponseEntity.ok(ShopUsersDto.fromShop(shop.get()).toString());
            }
            return ResponseEntity.ok(ShopDto.fromShop(shop.get()).toString());
        }
    }

    public ResponseEntity<String> filter(String address, String phone, HttpServletRequest request){
        Iterable<Shop> shops = myShopRepository.filter(address, phone);
        if (!userService.checkAdmin(request)) {
            List<ShopUsersDto> shops2 = new ArrayList<>();
            for (Shop shop : shops){
                shops2.add(ShopUsersDto.fromShop(shop));
            }
            return new ResponseEntity<>(shops2.toString(), HttpStatus.OK);
        }
        List<ShopDto> shops2 = new ArrayList<>();
        for (Shop shop : shops){
            shops2.add(ShopDto.fromShop(shop));
        }
        return ResponseEntity.ok(shops2.toString());
    }
}
