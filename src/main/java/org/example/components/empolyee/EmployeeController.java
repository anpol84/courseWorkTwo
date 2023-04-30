package org.example.components.empolyee;


import lombok.RequiredArgsConstructor;

import org.example.components.address.Address;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("")
    public String findAll(Model model){
        List<Employee> employees =  employeeService.findAll();
        model.addAttribute("employees", employees);

        return "employee";
    }
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        model.addAttribute("employee", employeeService.findById(id));
        return "employee_info";
    }
    @GetMapping("/update")
    public String updatePage(@RequestParam Long id, @RequestParam Long shop_id,
                             @ModelAttribute Employee employee, Model model){
        model.addAttribute("id", id);
        model.addAttribute("shop_id", shop_id);
        model.addAttribute("employee", employee);
        return "employee_put";

    }
    @PostMapping
    public String save(@ModelAttribute Employee employee, @RequestParam Long shop_id, Model model){
        if (employeeService.checkShop(shop_id)){
            model.addAttribute("message", "No such shop");
            return findAll(model);
        }
        employeeService.save(employee, shop_id);
        return findAll(model);

    }
    @DeleteMapping
    public String deleteById(@RequestParam Long id, Model model){
        employeeService.deleteById(id);
        return findAll(model);
    }

    @PutMapping
    public String update(@ModelAttribute Employee employee, @RequestParam Long id,
                                         @RequestParam Long shop_id, Model model){
        if (shop_id != null && employeeService.checkShop(shop_id)){
            model.addAttribute("message", "No such shop");
            return findAll(model);
        }
        employeeService.update(employee, id, shop_id);
        return findAll(model);
    }

}
