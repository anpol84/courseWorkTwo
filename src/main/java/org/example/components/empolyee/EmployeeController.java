package org.example.components.empolyee;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("")
    public ResponseEntity<String> findAll(@RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "email", required = false) String email,
                                          @RequestParam(value = "phone", required = false) String phone,
                                          @RequestParam(value = "salary", required = false) Double salary,
                                          @RequestParam(value = "position", required = false) String position,
                                          @NonNull HttpServletRequest request){
        if (name != null || email != null || phone != null || salary != null || position != null) {
            return employeeService.filter(name, email, phone, salary, position, request);
        } else {
            return employeeService.findAll(request);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable Long id, @NonNull HttpServletRequest request){
        return employeeService.findById(id, request);
    }
    @PostMapping
    public ResponseEntity<String> save(@RequestBody Employee employee, @NonNull HttpServletRequest request){
        return employeeService.save(employee, request);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteById(@RequestParam Long id, @NonNull HttpServletRequest request){
        return employeeService.deleteById(id, request);
    }

}
