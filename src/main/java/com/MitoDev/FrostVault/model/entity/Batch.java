package com.MitoDev.FrostVault.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer batchNumber;

    private LocalDate dueDate;

    private Integer initialQuantity;

    private Integer currentQuantity;

    private LocalDate manufacturingDate;

    private LocalDateTime manufacturingTime;

    @ManyToOne
    private Product product;

    @ManyToOne
    @JoinColumn(name="id_section")
    private Section section;
}
