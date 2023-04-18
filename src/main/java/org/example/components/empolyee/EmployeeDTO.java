package org.example.components.empolyee;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import liquibase.pro.packaged.E;
import lombok.Data;
import org.example.components.address.AddressDto;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDTO {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private double salary;
    private String position;
    private String shopAddress;

    private String shopPhone;
    private String address;


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
        if (employee.getAddress() != null){
            employeeDTO.setAddress(employee.getAddress().toString());
        }else{
            employeeDTO.setAddress("[]");
        }
        if (employee.getShop() == null){
            employeeDTO.setShopAddress("[]");
            employeeDTO.setShopPhone("[]");

            return employeeDTO;
        }
        if (employee.getShop().getAddress() != null){
            employeeDTO.setShopAddress(employee.getShop().getAddress().toString());
        }else{
            employeeDTO.setShopAddress("[]");
        }

        employeeDTO.setShopPhone(employee.getShop().getPhone());


        return employeeDTO;
    }
}
