package org.example.components.empolyee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.example.components.shop.Shop;

import javax.persistence.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String email;
    private double salary;
    private String position;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id")
    private Shop shop;


}
