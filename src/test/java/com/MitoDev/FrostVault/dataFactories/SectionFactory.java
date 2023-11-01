package com.MitoDev.FrostVault.dataFactories;

import com.MitoDev.FrostVault.model.dto.SectionDTO;
import com.MitoDev.FrostVault.model.entity.Section;
import com.MitoDev.FrostVault.model.entity.enums.Type;

public class SectionFactory {

    public static Section section1(){
        return Section.builder()
                .sectionCode(1)
                .sectionType(Type.FRESH)
                .capacity(200)
                .warehouse(WarehouseFactory.warehouse1())
                .build();
    }

    public static Section section2(){
        return Section.builder()
                .sectionCode(2)
                .sectionType(Type.FROZEN)
                .capacity(500)
                .warehouse(WarehouseFactory.warehouse1())
                .build();
    }

    public static Section section3(){
        return Section.builder()
                .sectionCode(3)
                .sectionType(Type.COOLED)
                .capacity(500)
                .warehouse(WarehouseFactory.warehouse2())
                .build();
    }

    public static SectionDTO sectionDTO1(){
        return SectionDTO.builder()
                .sectionCode(1)
                .warehouseCode(1)
                .build();
    }

    public static SectionDTO sectionDTO2(){
        return SectionDTO.builder()
                .sectionCode(2)
                .warehouseCode(1)
                .build();
    }

    public static SectionDTO sectionDTO3(){
        return SectionDTO.builder()
                .sectionCode(3)
                .warehouseCode(2)
                .build();
    }
}
