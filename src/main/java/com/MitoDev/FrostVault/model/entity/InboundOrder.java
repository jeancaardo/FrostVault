package com.MitoDev.FrostVault.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
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


    @OneToMany
    @JoinColumn(name="id_batch")
    private List<Batch> batches;
}
