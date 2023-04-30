package org.example.components.category;

import lombok.Data;
import org.example.components.item.Item;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name="average_size")
    String averageSize;

    String purpose;
    @OneToMany(mappedBy = "category")
    private List<Item> items = new ArrayList<>();


}
