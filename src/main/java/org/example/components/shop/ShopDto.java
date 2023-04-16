package org.example.components.shop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopDto {
    private Long id;
    private String address;
    private String phone;
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
        return shopDto;
    }
}
