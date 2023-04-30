package org.example.components.empolyee;


import org.example.components.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final ShopRepository shopRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ShopRepository shopRepository) {
        this.employeeRepository = employeeRepository;
        this.shopRepository = shopRepository;
    }

    public List<Employee> findAll() {

       return employeeRepository.findAll();
    }
    public void save(Employee employee, Long shop_id){
        employee.setShop(shopRepository.getById(shop_id));
        employeeRepository.save(employee);
    }

    public void deleteById(Long id){
        employeeRepository.deleteById(id);
    }



    public Employee findById(Long id){
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.get();
    }

    public void update(Employee employee, Long id, Long shop_id){
        Optional<Employee> employee1 = employeeRepository.findById(id);
        if (shop_id != null){
            employee.setShop(shopRepository.getById(shop_id));
        }else{
            employee.setShop(employee1.get().getShop());
        }
        employee.setId(id);
        employeeRepository.save(employee);
    }

    public boolean checkShop(Long id){
        return shopRepository.getById(id) == null;
    }
}
