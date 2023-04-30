package org.example.components.address;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/addresses")
public class AddressController {
    private final AddressService addressService;
    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public String findAll(Model model){
        List<Address> addresses =  addressService.findAll();
        model.addAttribute("addresses", addresses);
        return "address";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        model.addAttribute("address", addressService.findById(id));
        return "address_info";
    }
    @GetMapping("/update")
    public String updatePage(@RequestParam Long id,@RequestParam(required = false) Long employee_id,
                             @RequestParam(required = false) Long shop_id, @ModelAttribute Address address, Model model){
        model.addAttribute("id", id);
        model.addAttribute("address", address);
        model.addAttribute("employee_id", employee_id);
        model.addAttribute("shop_id", shop_id);

        return "address_put";
    }
    @PostMapping
    public String save(@ModelAttribute Address address, Model model,
                                       @RequestParam(name="employee_id", required = false) Long employee_id,
                                       @RequestParam(name="shop_id", required = false) Long shop_id){
        if (shop_id != null && employee_id != null || shop_id == null && employee_id == null){
            model.addAttribute("message", "Error with entities");
            return findAll(model);
        }
        if (employee_id != null){
            addressService.save(address, true, employee_id);
        }else{
            addressService.save(address, false, shop_id);
        }
        return findAll(model);
    }
    @DeleteMapping
    public String deleteById(@RequestParam Long id, Model model){
        addressService.deleteById(id);
        return findAll(model);
    }

    @PutMapping
    public String update(@ModelAttribute Address address, @RequestParam Long id, Model model,
                                         @RequestParam(required = false) Long shop_id,
                                         @RequestParam(required = false) Long employee_id){
        if (shop_id != null && employee_id != null ){
            model.addAttribute("message", "Error with entities");
            return findAll(model);
        }
        if (shop_id != null && addressService.checkShop(shop_id)){
            model.addAttribute("message", "No such shop");
            return findAll(model);
        }
        if (employee_id != null && addressService.checkEmployee(employee_id)){
            model.addAttribute("message", "No such employee");
            return findAll(model);
        }
        addressService.update(address, id, shop_id, employee_id);
        return findAll(model);
    }
}
