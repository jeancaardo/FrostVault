package com.MitoDev.FrostVault.dataFactories;


import com.MitoDev.FrostVault.model.dto.InboundOrderRequestDTO;
import com.MitoDev.FrostVault.model.entity.InboundOrder;

import java.time.LocalDate;

public class InboundOrderFactory {

    public static InboundOrder inboundOrderNew1(){
        return InboundOrder.builder()
                .orderDate(LocalDate.of(2020, 2, 20))
                .batches(BatchFactory.listOfBatchesPersisted())
                .section(SectionFactory.section1())
                .build();
    }

    public static InboundOrder inboundOrderPersisted1(){
        return InboundOrder.builder()
                .orderNumber(1)
                .orderDate(LocalDate.of(2020, 2, 20))
                .batches(BatchFactory.listOfBatchesPersisted())
                .section(SectionFactory.section1())
                .build();
    }

    public static InboundOrder inboundOrderToUpdate(){
        return InboundOrder.builder()
                .orderNumber(1)
                .orderDate(LocalDate.of(2020, 2, 20))
                .batches(BatchFactory.listOfBatchesPersistedToUpdate())
                .section(SectionFactory.section1())
                .build();
    }

    public static InboundOrder inboundOrderToUpdateWithBatchesSaved(){
        return InboundOrder.builder()
                .orderNumber(1)
                .orderDate(LocalDate.of(2020, 2, 20))
                .batches(BatchFactory.listOfBatchPersisted2WithTooMuchQuantity())
                .section(SectionFactory.section1())
                .build();
    }

    public static InboundOrder inboundOrderUpdated1(){
        return InboundOrder.builder()
                .orderNumber(1)
                .orderDate(LocalDate.of(2020, 2, 20))
                .batches(BatchFactory.listOfBatchesPersisted2())
                .section(SectionFactory.section1())
                .build();
    }

    public static InboundOrderRequestDTO inboundOrderRequestDTO1(){
        return InboundOrderRequestDTO.builder()
                .orderDate(LocalDate.of(2020, 2, 20))
                .batchStock(BatchFactory.listOfBatchesRequestDTO1())
                .section(SectionFactory.sectionDTO1())
                .build();
    }

    public static InboundOrderRequestDTO inboundOrderRequestExistentDTO1(){
        return InboundOrderRequestDTO.builder()
                .orderNumber(1)
                .orderDate(LocalDate.of(2020, 2, 20))
                .batchStock(BatchFactory.listOfBatchesRequestDTO2())
                .section(SectionFactory.sectionDTO1())
                .build();
    }

    public static InboundOrderRequestDTO inboundOrderRequestExistentDTOWithTooMuchQuantity(){
        return InboundOrderRequestDTO.builder()
                .orderNumber(1)
                .orderDate(LocalDate.of(2020, 2, 20))
                .batchStock(BatchFactory.listOfBatchesRequestDTO2WithBatchesTooMuchQuantity())
                .section(SectionFactory.sectionDTO1())
                .build();
    }

}
