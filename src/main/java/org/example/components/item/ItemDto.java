package org.example.components.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDto {
    private Long id;
    private String category;
    private double purchasePrice;
    private double sellingPrice;
    private String shopAddress;
    private String kind;

    private String shopPhone;
    public Item toItem(){
        Item item = new Item();
        item.setId(id);
        item.setPurchasePrice(purchasePrice);
        item.setSellingPrice(sellingPrice);
        return item;
    }
    public static ItemDto fromItem(Item item){
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setCategory(item.getCategory().getName());
        itemDto.setKind(item.getKind().getName());
        itemDto.setPurchasePrice(item.getPurchasePrice());
        itemDto.setSellingPrice(item.getSellingPrice());
        itemDto.setShopAddress(item.getShop().getAddress().toString());
        itemDto.setShopPhone(item.getShop().getPhone());
        return itemDto;
    }
}
