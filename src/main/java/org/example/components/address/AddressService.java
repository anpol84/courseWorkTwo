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

    public ResponseEntity<?> findAll(HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Iterable<Address> addresses = addressRepository.findAll();
        List<AddressDto> addresses2 = new ArrayList<>();
        for (Address address : addresses){
            addresses2.add(AddressDto.fromAddress(address));
        }
        return new ResponseEntity<>(addresses2, HttpStatus.OK);
    }

    public ResponseEntity<?> findById(Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Optional<Address> address = addressRepository.findById(id);
        if (!address.isPresent()){
            return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(address.get(), HttpStatus.OK);
    }

    public ResponseEntity<?> filter(String region, String city, String street,
                                         Integer house, Integer flat, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Iterable<Address> addresses = myAddressRepository.filter(region, city, street, house, flat);
        List<AddressDto> addresses2 = new ArrayList<>();
        for (Address address : addresses){
            addresses2.add(AddressDto.fromAddress(address));
        }
        return new ResponseEntity<>(addresses2, HttpStatus.OK);
    }

    public ResponseEntity<String> save(Address address, boolean flag, Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        if (flag){
            if (employeeRepository.getById(id) == null){
                return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
            }
            address.setEmployee(employeeRepository.getById(id));
        }else{
            if (shopRepository.getById(id) == null){
                return new ResponseEntity<>("Shop not found", HttpStatus.NOT_FOUND);
            }
            address.setShop(shopRepository.getById(id));
        }
        addressRepository.save(address);
        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteById(Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        if (addressRepository.getById(id) == null){
            return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
        }
        addressRepository.deleteById(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    public ResponseEntity<String> update(Address address, Long id, Long shop_id, Long employee_id,
                                         HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Optional<Address> address1 = addressRepository.findById(id);
        if (!address1.isPresent()){
            return new ResponseEntity<>("Entity not found", HttpStatus.OK);
        }
        if (shop_id != null) {
            address.setShop(shopRepository.getById(shop_id));
        }else{
            address.setShop(address1.get().getShop());
        }
        if (employee_id != null){
            address.setEmployee(employeeRepository.getById(employee_id));
        }else{
            address.setEmployee(address1.get().getEmployee());
        }
        address.setId(id);
        addressRepository.save(address);
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }
}
