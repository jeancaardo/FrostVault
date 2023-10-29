package com.MitoDev.FrostVault.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class InboundOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderNumber;

    private LocalDate orderDate;

    @ManyToOne
    @JoinColumn(name = "id_section")
    private Section section;


    @OneToOne
    @JoinColumn(name="id_batch")
    private Batch batch;
}
