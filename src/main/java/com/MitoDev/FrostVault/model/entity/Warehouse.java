package com.MitoDev.FrostVault.model.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "warehouses")
@ToString
@EqualsAndHashCode
@Builder
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String address;

    @OneToMany(mappedBy = "warehouse",cascade=CascadeType.ALL)
    private List<Section> sectionList;
}
