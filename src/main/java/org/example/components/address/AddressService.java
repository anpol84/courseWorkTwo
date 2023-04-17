package org.example.components.address;

import liquibase.pro.packaged.P;
import lombok.NonNull;
import org.example.auth.UserService;
import org.example.components.empolyee.Employee;
import org.example.components.empolyee.EmployeeRepository;
import org.example.components.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final MyAddressRepository myAddressRepository;
    private final UserService userService;
    private final EmployeeRepository employeeRepository;
    private final ShopRepository shopRepository;
    @Autowired
    public AddressService(AddressRepository addressRepository, UserService userService,
                          MyAddressRepository myAddressRepository, EmployeeRepository employeeRepository,
                          ShopRepository shopRepository) {
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.myAddressRepository = myAddressRepository;
        this.employeeRepository = employeeRepository;
        this.shopRepository = shopRepository;
    }

    public ResponseEntity<String> findAll(HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Iterable<Address> addresses = addressRepository.findAll();
        List<AddressDto> addresses2 = new ArrayList<>();
        for (Address address : addresses){
            addresses2.add(AddressDto.fromAddress(address));
        }
        return new ResponseEntity<>(addresses2.toString(), HttpStatus.OK);
    }

    public ResponseEntity<String> findById(Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Optional<Address> address = addressRepository.findById(id);
        return address.map(value -> new ResponseEntity<>(AddressDto.fromAddress(value).toString(), HttpStatus.OK)).
                orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied"));
    }

    public ResponseEntity<String> filter(String region, String city, String street,
                                         Integer house, Integer flat, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Iterable<Address> addresses = myAddressRepository.filter(region, city, street, house, flat);
        List<AddressDto> addresses2 = new ArrayList<>();
        for (Address address : addresses){
            addresses2.add(AddressDto.fromAddress(address));
        }
        return new ResponseEntity<>(addresses2.toString(), HttpStatus.OK);
    }

    public ResponseEntity<String> save(Address address, boolean flag, Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        if (flag){
            address.setEmployee(employeeRepository.getById(id));
        }else{
            address.setShop(shopRepository.getById(id));
        }
        addressRepository.save(address);
        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteById(Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        addressRepository.deleteById(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
