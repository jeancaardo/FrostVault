package com.MitoDev.FrostVault.model.entity;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "purchaseorders")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrder;

    private LocalDate date;

    private String statusCode;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<PurchaseOrderDetail> purchaseOrderDetails;

    @ManyToOne()
    @JoinColumn(name = "id_user")
    private User user;
}
