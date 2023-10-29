package com.MitoDev.FrostVault.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;


import jakarta.persistence.*;
import java.util.List;



@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduct;

    private String name;

    private Integer totalQuantity;

    private Integer price;

    private Double temperature;

    @Enumerated(EnumType.ORDINAL)
    private Type productType;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Batch> batchList;

}
