package org.example.components.item;

import org.example.auth.User;
import org.example.auth.UserService;
import org.example.components.empolyee.Employee;
import org.example.components.kind.KindRepository;
import org.example.components.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final MyItemRepository myItemRepository;
    private final UserService userService;
    private final ShopRepository shopRepository;
    private final KindRepository kindRepository;
    @Autowired
    public ItemService(ItemRepository itemRepository, MyItemRepository myItemRepository,
                       UserService userService, ShopRepository shopRepository, KindRepository kindRepository) {
        this.itemRepository = itemRepository;
        this.myItemRepository = myItemRepository;
        this.userService = userService;
        this.shopRepository = shopRepository;
        this.kindRepository = kindRepository;
    }

    public ResponseEntity<String> findAll(){
        Iterable<Item> items = itemRepository.findAll();
        List<ItemDto> items2 = new ArrayList<>();
        for (Item item : items){
            items2.add(ItemDto.fromItem(item));
        }
        return ResponseEntity.ok(items2.toString());
    }

    public ResponseEntity<String> save(Item item, HttpServletRequest request, Long shop_id, Long kind_id){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        item.setShop(shopRepository.getById(shop_id));
        item.setKind(kindRepository.getById(kind_id));
        itemRepository.save(item);
        return ResponseEntity.ok("Saved");
    }

    public ResponseEntity<String> deleteById(Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        itemRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    public ResponseEntity<String> findById(Long id){
        Optional<Item> item = itemRepository.findById(id);
        if (!item.isPresent()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }else{
            return ResponseEntity.ok((ItemDto.fromItem(item.get())).toString());
        }
    }

    public ResponseEntity<String> filter(String category, Double purchasePrice, Double sellingPrice){
        Iterable<Item> items = myItemRepository.filter(category, purchasePrice, sellingPrice);
        List<ItemDto> items2 = new ArrayList<>();
        for (Item item : items){
            items2.add(ItemDto.fromItem(item));
        }
        return ResponseEntity.ok(items2.toString());
    }
}
