package com.MitoDev.FrostVault.model.entity;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sections")
@ToString
@EqualsAndHashCode
@Builder
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_section")
    private Integer sectionCode;

    private Integer capacity;

    @Enumerated(EnumType.ORDINAL)
    private Type sectionType;

    @ManyToOne()
    @JoinColumn(name = "warehouse_id",nullable = false)
    private Warehouse warehouse;

}
