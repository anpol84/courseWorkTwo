package org.example.components.address;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {
    private final AddressService addressService;
    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name = "region", required = false) String region,
                                          @RequestParam(name="city", required = false) String city,
                                          @RequestParam(name="street", required = false) String street,
                                          @RequestParam(name="house", required = false) Integer house,
                                          @RequestParam(name="flat", required = false) Integer flat,
                                          @NonNull HttpServletRequest request){
        if (region != null || city != null || street != null || house != null || flat != null){
            return addressService.filter(region, city, street, house, flat, request);
        }
        return addressService.findAll(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id, @NonNull HttpServletRequest request){
        return addressService.findById(id, request);
    }
    @PostMapping
    public ResponseEntity<String> save(@RequestBody Address address, @NonNull HttpServletRequest request,
                                       @RequestParam(name="employee_id", required = false) Long employee_id,
                                       @RequestParam(name="shop_id", required = false) Long shop_id){
        if (shop_id != null && employee_id != null || shop_id == null && employee_id == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        if (employee_id != null){
            return addressService.save(address, true, employee_id, request);
        }else{
            return addressService.save(address, false, shop_id, request);
        }
    }
    @DeleteMapping
    public ResponseEntity<String> deleteById(@RequestParam Long id, @NonNull HttpServletRequest request){
        return addressService.deleteById(id, request);
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Address address, @RequestParam Long id,
                                         @RequestParam(required = false) Long shop_id,
                                         @RequestParam(required = false) Long employee_id,
                                         @NonNull HttpServletRequest request){
        if (shop_id != null && employee_id != null ){
            return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
        }
        return addressService.update(address, id, shop_id, employee_id, request);
    }
}
