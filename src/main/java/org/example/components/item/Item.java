package org.example.components.item;

import lombok.*;
import org.example.components.category.Category;
import org.example.components.kind.Kind;
import org.example.components.shop.Shop;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kind_id")
    private Kind kind;
    @Column(name = "purchase_price")
    private double purchasePrice;
    @Column(name = "selling_price")
    private double sellingPrice;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id")
    private Shop shop;
}
