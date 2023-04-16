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

    public Pet toPet(){
        Pet pet = new Pet();
        pet.setId(id);
        pet.setKind(kind);
        pet.setAlias(alias);
        pet.setGender(gender);
        pet.setColor(color);
        pet.setPrice(price);
        return pet;
    }

    public static PetDto fromPet(Pet pet){
        PetDto petDto = new PetDto();
        petDto.setId(pet.getId());
        petDto.setKind(pet.getKind());
        petDto.setAlias(pet.getAlias());
        petDto.setGender(pet.getGender());
        petDto.setColor(pet.getColor());
        petDto.setPrice(pet.getPrice());
        return petDto;
    }

}
