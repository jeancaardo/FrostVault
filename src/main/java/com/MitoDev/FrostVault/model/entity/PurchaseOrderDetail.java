package com.MitoDev.FrostVault.model.entity;


import lombok.*;

import jakarta.persistence.*;
@Data
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


}
