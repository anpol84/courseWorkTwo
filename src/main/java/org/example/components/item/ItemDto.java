package org.example.components.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDto {
    private Long id;
    private String category;
    private String pet;
    private double purchasePrice;
    private double sellingPrice;
    public Item toItem(){
        Item item = new Item();
        item.setId(id);
        item.setCategory(category);
        item.setPet(pet);
        item.setPurchasePrice(purchasePrice);
        item.setSellingPrice(sellingPrice);
        return item;
    }
    public static ItemDto fromItem(Item item){
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setCategory(item.getCategory());
        itemDto.setPet(item.getPet());
        itemDto.setPurchasePrice(item.getPurchasePrice());
        itemDto.setSellingPrice(item.getSellingPrice());
        return itemDto;
    }
}
