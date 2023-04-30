package org.example.components.kind;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.example.components.item.Item;
import org.example.components.item.ItemDto;
import org.example.components.pet.Pet;
import org.example.components.pet.PetDto;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KindDto {
    private Long id;
    private String name;
    private String eatingWay;
    private String climateZone;
    private String order;
    private List<PetDto> pets;
    private List<ItemDto> items;

    public Kind toKind(){
        Kind kind = new Kind();
        kind.setId(id);
        kind.setName(name);
        kind.setEatingWay(eatingWay);
        kind.setClimateZone(climateZone);
        kind.setOrder(order);
        return kind;
    }

    public static KindDto fromKind(Kind kind){
        KindDto kindDto = new KindDto();
        kindDto.setId(kind.getId());
        kindDto.setName(kind.getName());
        kindDto.setEatingWay(kind.getEatingWay());
        kindDto.setClimateZone(kind.getClimateZone());
        kindDto.setOrder(kind.getOrder());

        List<PetDto> pets = new ArrayList<>();

        for (Pet pet : kind.getPets()){
            PetDto petDto = PetDto.fromPet(pet);
            petDto.setKind("");
            pets.add(petDto);
        }
        kindDto.setPets(pets);
        List<ItemDto> items = new ArrayList<>();
        for (Item item : kind.getItems()){
            ItemDto itemDto = ItemDto.fromItem(item);
            itemDto.setKind(null);
            itemDto.setCategory(null);
            items.add(itemDto);
        }
        kindDto.setItems(items);
        return kindDto;
    }
}
