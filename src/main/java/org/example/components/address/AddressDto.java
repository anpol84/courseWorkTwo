package org.example.components.address;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.example.components.empolyee.EmployeeDTO;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDto {
    private Long id;

    private String region;
    private String city;
    private String street;
    private Integer house;
    private Integer flat;


    public Address toAddress(){
        Address address = new Address();
        address.setId(id);
        address.setRegion(region);
        address.setCity(city);
        address.setStreet(street);
        address.setHouse(house);
        address.setFlat(flat);
        return address;
    }

    public static AddressDto fromAddress(Address address){
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setRegion(address.getRegion());
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setHouse(address.getHouse());
        addressDto.setFlat(address.getFlat());

        return addressDto;
    }
}
