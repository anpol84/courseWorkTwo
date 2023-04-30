package org.example.components.item;


import org.example.components.category.CategoryRepository;

import org.example.components.kind.KindRepository;
import org.example.components.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    private final ShopRepository shopRepository;
    private final KindRepository kindRepository;
    private final CategoryRepository categoryRepository;
    @Autowired
    public ItemService(ItemRepository itemRepository, ShopRepository shopRepository,
                       KindRepository kindRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.shopRepository = shopRepository;
        this.kindRepository = kindRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public void save(Item item, Long shop_id, Long kind_id,
                                       Long category_id){

        item.setShop(shopRepository.getById(shop_id));
        item.setKind(kindRepository.getById(kind_id));
        item.setCategory(categoryRepository.getById(category_id));
        itemRepository.save(item);
    }

    public void deleteById(Long id){
        itemRepository.deleteById(id);
    }

    public Item findById(Long id){
        return itemRepository.findById(id).get();
    }



    public void update(Item item, Long id, Long shop_id, Long category_id, Long kind_id){

        Optional<Item> item1 = itemRepository.findById(id);
        if (shop_id == null){
            item.setShop(item1.get().getShop());
        }else{
            item.setShop(shopRepository.getById(shop_id));
        }
        if (category_id == null){
            item.setCategory(item1.get().getCategory());
        }else{
            item.setCategory(categoryRepository.getById(category_id));
        }
        if (kind_id == null){
            item.setKind(item1.get().getKind());
        }else{
            item.setKind(kindRepository.getById(kind_id));
        }
        item.setId(id);
        itemRepository.save(item);
    }

    public boolean checkShop(Long id){
        return shopRepository.getById(id) == null;
    }

    public boolean checkKind(Long id ){
        return !kindRepository.findById(id).isPresent();
    }

    public boolean checkCategory(Long id ){
        return !categoryRepository.findById(id).isPresent();
    }
}
