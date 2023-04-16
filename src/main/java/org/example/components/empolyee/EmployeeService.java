package org.example.components.empolyee;

import org.example.auth.UserService;
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
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, MyEmployeeRepository myEmployeeRepository,
                           UserService userService, ShopRepository shopRepository) {
        this.employeeRepository = employeeRepository;
        this.myEmployeeRepository = myEmployeeRepository;
        this.userService = userService;
        this.shopRepository = shopRepository;
    }

    public ResponseEntity<String> findAll(HttpServletRequest request) {
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Iterable<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employees2 = new ArrayList<>();
        for (Employee employee : employees){
            employees2.add(EmployeeDTO.fromEmployee(employee));
        }

        return ResponseEntity.ok(employees2.toString());
    }
    public ResponseEntity<String> save(Employee employee, HttpServletRequest request, Long shop_id){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        employee.setShop(shopRepository.getById(shop_id));
        employeeRepository.save(employee);
        return ResponseEntity.ok("Saved");
    }

    public ResponseEntity<String>  deleteById(Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        employeeRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    public ResponseEntity<String> filter(String name, String email, String phone, Double salary, String position,
                                         HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Iterable<Employee> employees = myEmployeeRepository.filter(name, email, phone, salary, position);
        List<EmployeeDTO> employees2 = new ArrayList<>();
        for (Employee employee : employees){
            employees2.add(EmployeeDTO.fromEmployee(employee));
        }
        return ResponseEntity.ok(employees2.toString());
    }

    public ResponseEntity<String> findById(Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Optional<Employee> employee = employeeRepository.findById(id);
        if (!employee.isPresent()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }else{
            return ResponseEntity.ok((EmployeeDTO.fromEmployee(employee.get())).toString());
        }
    }
}
