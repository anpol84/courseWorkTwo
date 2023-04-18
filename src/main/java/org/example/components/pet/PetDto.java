package org.example.components.pet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PetDto {
    private Long id;
    private String kind;
    private double weight;
    private String alias;
    private String gender;
    private String color;
    private double price;

    private String shopAddress;

    private String shopPhone;

    public Pet toPet(){
        Pet pet = new Pet();
        pet.setId(id);
        pet.setAlias(alias);
        pet.setWeight(weight);
        pet.setGender(gender);
        pet.setColor(color);
        pet.setPrice(price);
        return pet;
    }

    public static PetDto fromPet(Pet pet){
        PetDto petDto = new PetDto();
        petDto.setId(pet.getId());
        if (pet.getKind() != null){
            petDto.setKind(pet.getKind().getName());
        }else{
            petDto.setKind("[]");
        }

        petDto.setAlias(pet.getAlias());
        petDto.setWeight(pet.getWeight());
        petDto.setGender(pet.getGender());
        petDto.setColor(pet.getColor());
        petDto.setPrice(pet.getPrice());
        if (pet.getShop() == null){
            petDto.setShopAddress("[]");
            petDto.setShopPhone("[]");
        }else{
            petDto.setShopPhone(pet.getShop().getPhone());
            if (pet.getShop().getAddress() == null){
                petDto.setShopAddress("[]");
            }else{
                petDto.setShopAddress(pet.getShop().getAddress().toString());
            }
        }
        return petDto;
    }

}
