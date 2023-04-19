package org.example.components.empolyee;

import liquibase.pro.packaged.R;
import org.example.auth.UserService;
import org.example.components.address.AddressRepository;
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
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final MyEmployeeRepository myEmployeeRepository;
    private final ShopRepository shopRepository;
    private final UserService userService;
    private final AddressRepository addressRepository;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, MyEmployeeRepository myEmployeeRepository,
                           UserService userService, ShopRepository shopRepository,
                           AddressRepository addressRepository) {
        this.employeeRepository = employeeRepository;
        this.myEmployeeRepository = myEmployeeRepository;
        this.userService = userService;
        this.shopRepository = shopRepository;
        this.addressRepository = addressRepository;
    }

    public ResponseEntity<?> findAll(HttpServletRequest request) {
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Iterable<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employees2 = new ArrayList<>();
        for (Employee employee : employees){
            employees2.add(EmployeeDTO.fromEmployee(employee));
        }

        return ResponseEntity.ok(employees2);
    }
    public ResponseEntity<String> save(Employee employee, HttpServletRequest request, Long shop_id){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        if (shopRepository.getById(shop_id) == null){
            return new ResponseEntity<>("Shop not found", HttpStatus.NOT_FOUND);
        }
        employee.setShop(shopRepository.getById(shop_id));
        employeeRepository.save(employee);
        return ResponseEntity.ok("Saved");
    }

    public ResponseEntity<String>  deleteById(Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        if (!(employeeRepository.findById(id)).isPresent()){
            return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
        }
        employeeRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    public ResponseEntity<?> filter(String name, String email, String phone, Double salary, String position,
                                         HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Iterable<Employee> employees = myEmployeeRepository.filter(name, email, phone, salary, position);
        List<EmployeeDTO> employees2 = new ArrayList<>();
        for (Employee employee : employees){
            employees2.add(EmployeeDTO.fromEmployee(employee));
        }
        return ResponseEntity.ok(employees2);
    }

    public ResponseEntity<?> findById(Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Optional<Employee> employee = employeeRepository.findById(id);
        if (!employee.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity not found");
        }else{
            return ResponseEntity.ok((EmployeeDTO.fromEmployee(employee.get())));
        }
    }

    public ResponseEntity<String> update(Employee employee, Long id, Long shop_id,
                                         HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Optional<Employee> employee1 = employeeRepository.findById(id);
        if (!employee1.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity not found");
        }
        if (shop_id != null){
            if (!(shopRepository.findById(shop_id)).isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shop not found");
            }
            employee.setShop(shopRepository.getById(shop_id));
        }else{
            employee.setShop(employee1.get().getShop());
        }
        employee.setId(id);
        employeeRepository.save(employee);
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }
}
