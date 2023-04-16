package org.example.components.pet;

import lombok.*;
import org.example.components.shop.Shop;

import javax.persistence.*;

@Entity
@Table(name = "pets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kind;
    private double weight;
    private String alias;
    private String gender;
    private String color;
    private double price;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id")
    private Shop shop;
}
