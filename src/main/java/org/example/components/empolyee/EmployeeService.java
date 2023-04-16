package org.example.components.empolyee;

import org.example.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final MyEmployeeRepository myEmployeeRepository;
    private final UserService userService;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, MyEmployeeRepository myEmployeeRepository,
                           UserService userService) {
        this.employeeRepository = employeeRepository;
        this.myEmployeeRepository = myEmployeeRepository;
        this.userService = userService;
    }

    public ResponseEntity<String> findAll(HttpServletRequest request) {
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Iterable<Employee> employees = employeeRepository.findAll();
        return ResponseEntity.ok(employees.toString());
    }
    public ResponseEntity<String> save(Employee employee, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
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
        return ResponseEntity.ok(employees.toString());
    }

    public ResponseEntity<String> findById(Long id, HttpServletRequest request){
        if (!userService.checkAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Optional<Employee> employee = employeeRepository.findById(id);
        if (!employee.isPresent()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }else{
            return ResponseEntity.ok(employee.toString());
        }
    }
}
