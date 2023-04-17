package org.example.components.shop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.example.components.empolyee.Employee;
import org.example.components.empolyee.EmployeeDTO;
import org.example.components.item.Item;
import org.example.components.item.ItemDto;
import org.example.components.pet.Pet;
import org.example.components.pet.PetDto;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopUsersDto {
    private Long id;
    private String address;
    private String phone;
    private List<PetDto> pets = new ArrayList<>();
    private List<ItemDto> items = new ArrayList<>();
    public Shop toShop(){
        Shop shop = new Shop();

        shop.setId(id);
        shop.setPhone(phone);
        return shop;
    }

    public static ShopUsersDto fromShop(Shop shop){
        ShopUsersDto shopDto = new ShopUsersDto();
        shopDto.setId(shop.getId());
        if (shop.getAddress() != null) {
            shopDto.setAddress(shop.getAddress().toString());
        }else{
            shopDto.setAddress("[]");
        }
        shopDto.setPhone(shop.getPhone());
        List<PetDto> pets = new ArrayList<>();
        List<ItemDto> items = new ArrayList<>();

        for (Pet pet : shop.getPets()){
            pets.add(PetDto.fromPet(pet));
        }
        for (Item item : shop.getItems()){
            items.add(ItemDto.fromItem(item));
        }
        shopDto.setItems(items);
        shopDto.setPets(pets);
        return shopDto;
    }
}
