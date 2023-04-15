package org.example.components.empolyee;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;


    @GetMapping
    public Iterable<Employee> findAll(){
        return employeeService.findAll();
    }
    @PostMapping
    public void save(@RequestBody Employee employee){
        employeeService.save(employee);
    }
    @DeleteMapping
    public void deleteById(@RequestParam Long id){
        employeeService.deleteById(id);
    }
    @GetMapping(params = {"name", "email", "phone", "salary", "position"})
    public Iterable<Employee> filter(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "email", required = false) String email,
                                     @RequestParam(value = "phone", required = false) String phone,
                                     @RequestParam(value = "salary", required = false) Double salary,
                                     @RequestParam(value = "position", required = false) String position){
        return employeeService.filter(name, email, phone, salary, position);
    }
    @GetMapping("/{id}")
    public Optional<Employee> findById(@PathVariable Long id){
        return employeeService.findById(id);
    }
}
