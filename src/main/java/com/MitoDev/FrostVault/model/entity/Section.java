package com.MitoDev.FrostVault.model.entity;

import com.MitoDev.FrostVault.model.entity.enums.Type;
import lombok.*;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sectionCode;

    private Integer capacity;

    @Enumerated(EnumType.ORDINAL)
    private Type sectionType;

    @ManyToOne()
    private Warehouse warehouse;
}