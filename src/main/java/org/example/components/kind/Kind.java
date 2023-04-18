package org.example.components.kind;

import lombok.Data;
import org.example.components.item.Item;
import org.example.components.pet.Pet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kinds")
@Data
public class Kind {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "eating_way")
    private String eatingWay;
    @Column(name = "climate_zone")
    private String climateZone;
    @Column(name = "order_kind")
    private String order;
    @OneToMany(mappedBy = "kind")
    private List<Pet> pets = new ArrayList<>();
    @OneToMany(mappedBy = "kind")
    private List<Item> items = new ArrayList<>();

}
