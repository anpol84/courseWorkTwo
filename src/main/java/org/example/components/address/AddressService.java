package org.example.components.address;


import org.example.components.empolyee.EmployeeRepository;
import org.example.components.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    private final EmployeeRepository employeeRepository;
    private final ShopRepository shopRepository;
    @Autowired
    public AddressService(AddressRepository addressRepository, EmployeeRepository employeeRepository,
                          ShopRepository shopRepository) {
        this.addressRepository = addressRepository;

        this.employeeRepository = employeeRepository;
        this.shopRepository = shopRepository;
    }

    public List<Address> findAll(){
        return addressRepository.findAll();
    }

    public Address findById(Long id){
        Optional<Address> address = addressRepository.findById(id);
        return address.get();
    }

    public void save(Address address, boolean flag, Long id){
        if (flag){
            address.setEmployee(employeeRepository.getById(id));
        }else{
            address.setShop(shopRepository.getById(id));
        }
        addressRepository.save(address);
    }

    public void deleteById(Long id){
        addressRepository.deleteById(id);
    }

    public void update(Address address, Long id, Long shop_id, Long employee_id){
        Optional<Address> address1 = addressRepository.findById(id);
        if (shop_id != null) {
            if (address1.get().getEmployee() != null){
                return;
            }
            address.setShop(shopRepository.getById(shop_id));
        }else{
            address.setShop(address1.get().getShop());
        }
        if (employee_id != null){
            if (address1.get().getShop() != null){
                return;
            }
            address.setEmployee(employeeRepository.getById(employee_id));
        }else{
            address.setEmployee(address1.get().getEmployee());
        }
        address.setId(id);
        addressRepository.save(address);
    }

    public boolean checkShop(Long id){
        return shopRepository.getById(id) == null;
    }

    public boolean checkEmployee(Long id){
        return !employeeRepository.findById(id).isPresent();
    }
}
