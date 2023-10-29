package com.MitoDev.FrostVault.model.entity;


import lombok.*;

import jakarta.persistence.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "purchaseorderdetails")
public class PurchaseOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrderDetail;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name="id_product")
    private Product product;

    //Bidireccional
//    @ManyToOne()
//    @JoinColumn(name = "id_order", nullable = false)
//    private PurchaseOrder purchaseOrder;


}
