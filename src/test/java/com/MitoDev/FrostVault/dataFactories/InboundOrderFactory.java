package com.MitoDev.FrostVault.dataFactories;


import com.MitoDev.FrostVault.model.dto.BatchDTO;
import com.MitoDev.FrostVault.model.dto.InboundOrderRequestDTO;
import com.MitoDev.FrostVault.model.dto.SectionDTO;
import com.MitoDev.FrostVault.model.entity.Batch;
import com.MitoDev.FrostVault.model.entity.InboundOrder;
import com.MitoDev.FrostVault.model.entity.Section;

import java.time.LocalDate;
import java.util.Arrays;

public class InboundOrderFactory {

    public static InboundOrder createInboundOrder(Integer orderNumber, Section section, Batch... batches) {
        return InboundOrder.builder()
                .orderNumber(orderNumber)
                .orderDate(LocalDate.of(2020, 2, 20))
                .batches(Arrays.asList(batches))
                .section(section)
                .build();
    }

    public static InboundOrderRequestDTO createInboundOrderRequestDTO(Integer orderNumber, SectionDTO section, BatchDTO... batchDTOS) {
        return InboundOrderRequestDTO.builder()
                .orderNumber(orderNumber)
                .orderDate(LocalDate.of(2020, 2, 20))
                .batchStock(Arrays.asList(batchDTOS))
                .section(section)
                .build();
    }
}
