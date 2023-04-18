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
    private String head;
    private List<EmployeeDTO> employees = new ArrayList<>();
    private List<PetDto> pets = new ArrayList<>();
    private List<ItemDto> items = new ArrayList<>();
    public Shop toShop(){
        Shop shop = new Shop();
        shop.setId(id);
        shop.setPhone(phone);
        shop.setHead(head);
        return shop;
    }

    public static ShopDto fromShop(Shop shop){
        ShopDto shopDto = new ShopDto();
        shopDto.setId(shop.getId());
        if (shop.getAddress() != null) {
            shopDto.setAddress(shop.getAddress().toString());
        }else{
            shopDto.setAddress(null);
        }
        shopDto.setPhone(shop.getPhone());
        shopDto.setHead(shop.getHead());
        List<EmployeeDTO> employees = new ArrayList<>();
        List<PetDto> pets = new ArrayList<>();
        List<ItemDto> items = new ArrayList<>();
        for (Employee employee : shop.getEmployees()){
            EmployeeDTO employeeDTO = EmployeeDTO.fromEmployee(employee);
            employeeDTO.setShopAddress(null);
            employeeDTO.setShopPhone(null);
            employees.add(employeeDTO);
        }
        for (Pet pet : shop.getPets()){
            PetDto petDto = PetDto.fromPet(pet);
            petDto.setShopPhone(null);
            petDto.setShopAddress(null);
            pets.add(petDto);
        }
        for (Item item : shop.getItems()){
            ItemDto itemDto = ItemDto.fromItem(item);
            itemDto.setShopPhone(null);
            itemDto.setShopAddress(null);
            items.add(itemDto);
        }
        shopDto.setEmployees(employees);
        shopDto.setItems(items);
        shopDto.setPets(pets);
        return shopDto;
    }
}
