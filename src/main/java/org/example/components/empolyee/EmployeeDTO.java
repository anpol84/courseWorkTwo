package org.example.components.empolyee;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import liquibase.pro.packaged.E;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDTO {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private double salary;
    private String position;

    public Employee toEmployee(){
        Employee employee = new Employee();
        employee.setName(name);
        employee.setPhone(phone);
        employee.setPosition(position);
        employee.setSalary(salary);
        employee.setId(id);
        return employee;
    }

    public static EmployeeDTO fromEmployee(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSalary(employee.getSalary());
        employeeDTO.setPhone(employee.getPhone());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setPosition(employee.getPosition());
        return employeeDTO;
    }
}
