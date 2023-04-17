package org.example.components.shop;

import lombok.*;
import org.example.components.address.Address;
import org.example.components.empolyee.Employee;
import org.example.components.item.Item;
import org.example.components.pet.Pet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shops")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "shop")
    private Address address;
    private String phone;
    @OneToMany(mappedBy = "shop")
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(mappedBy = "shop")
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "shop")
    private List<Pet> pets = new ArrayList<>();
}
