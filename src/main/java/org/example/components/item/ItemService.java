package org.example.components.item;

import org.example.auth.User;
import org.example.auth.UserService;
import org.example.components.category.CategoryRepository;
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
    private final CategoryRepository categoryRepository;
    @Autowired
    public ItemService(ItemRepository itemRepository, MyItemRepository myItemRepository,
                       UserService userService, ShopRepository shopRepository, KindRepository kindRepository,
                       CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.myItemRepository = myItemRepository;
        this.userService = userService;
        this.shopRepository = shopRepository;
        this.kindRepository = kindRepository;
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<?> findAll(){
        Iterable<Item> items = itemRepository.findAll();
        List<ItemDto> items2 = new ArrayList<>();
        for (Item item : items){
            items2.add(ItemDto.fromItem(item));
        }
        return ResponseEntity.ok(items2);
    }

    public ResponseEntity<String> save(Item item, HttpServletRequest request, Long shop_id, Long kind_id,
                                       Long category_id){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        if (shopRepository.getById(shop_id) == null){
            return new ResponseEntity<>("Shop not found", HttpStatus.NOT_FOUND);
        }
        item.setShop(shopRepository.getById(shop_id));
        if (kindRepository.getById(kind_id) == null){
            return new ResponseEntity<>("Kind not found", HttpStatus.NOT_FOUND);
        }
        item.setKind(kindRepository.getById(kind_id));
        if (categoryRepository.getById(category_id) == null){
            return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
        }
        item.setCategory(categoryRepository.getById(category_id));
        itemRepository.save(item);
        return ResponseEntity.ok("Saved");
    }

    public ResponseEntity<String> deleteById(Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        if (itemRepository.getById(id) == null){
            return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
        }
        itemRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    public ResponseEntity<?> findById(Long id){
        Optional<Item> item = itemRepository.findById(id);
        if (!item.isPresent()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Entyity not found");
        }else{
            return ResponseEntity.ok((ItemDto.fromItem(item.get())));
        }
    }

    public ResponseEntity<?> filter(String category, Double purchasePrice, Double sellingPrice){
        Iterable<Item> items = myItemRepository.filter(category, purchasePrice, sellingPrice);
        List<ItemDto> items2 = new ArrayList<>();
        for (Item item : items){
            items2.add(ItemDto.fromItem(item));
        }
        return ResponseEntity.ok(items2);
    }

    public ResponseEntity<String> update(Item item, Long id, Long shop_id, Long category_id, Long kind_id,
                                         HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Item item1 = itemRepository.getById(id);
        if (item1 == null){
            return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
        }
        if (shop_id == null){
            item.setShop(item1.getShop());
        }else{
            item.setShop(shopRepository.getById(shop_id));
        }
        if (category_id == null){
            item.setCategory(item1.getCategory());
        }else{
            item.setCategory(categoryRepository.getById(category_id));
        }
        if (kind_id == null){
            item.setKind(item1.getKind());
        }else{
            item.setKind(kindRepository.getById(kind_id));
        }
        item.setId(id);
        itemRepository.save(item);
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }
}
