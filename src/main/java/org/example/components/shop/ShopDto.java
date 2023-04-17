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
public class ShopDto {
    private Long id;
    private String address;
    private String phone;
    private List<EmployeeDTO> employees = new ArrayList<>();
    private List<PetDto> pets = new ArrayList<>();
    private List<ItemDto> items = new ArrayList<>();
    public Shop toShop(){
        Shop shop = new Shop();
        shop.setAddress(address);
        shop.setId(id);
        shop.setPhone(phone);
        return shop;
    }

    public static ShopDto fromShop(Shop shop){
        ShopDto shopDto = new ShopDto();
        shopDto.setId(shop.getId());
        shopDto.setAddress(shop.getAddress());
        shopDto.setPhone(shop.getPhone());
        List<EmployeeDTO> employees = new ArrayList<>();
        List<PetDto> pets = new ArrayList<>();
        List<ItemDto> items = new ArrayList<>();
        for (Employee employee : shop.getEmployees()){
            employees.add(EmployeeDTO.fromEmployee(employee));
        }
        for (Pet pet : shop.getPets()){
            pets.add(PetDto.fromPet(pet));
        }
        for (Item item : shop.getItems()){
            items.add(ItemDto.fromItem(item));
        }
        shopDto.setEmployees(employees);
        shopDto.setItems(items);
        shopDto.setPets(pets);
        return shopDto;
    }
}
