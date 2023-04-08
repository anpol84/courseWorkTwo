package org.example.components.item;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String pet;
    @Column(name = "purchase_price")
    private double purchasePrice;
    @Column(name = "selling_price")
    private double sellingPrice;
}
