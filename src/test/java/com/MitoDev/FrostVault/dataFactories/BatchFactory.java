package com.MitoDev.FrostVault.dataFactories;

import com.MitoDev.FrostVault.model.dto.*;
import com.MitoDev.FrostVault.model.entity.Batch;
import com.MitoDev.FrostVault.model.entity.Product;
import com.MitoDev.FrostVault.model.entity.Section;
import com.MitoDev.FrostVault.model.entity.enums.Type;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class BatchFactory {

    private static final LocalDateTime MANUFACTURING_TIME = LocalDateTime.now().minus(2, ChronoUnit.YEARS);
    private static final LocalDate MANUFACTURING_DATE = LocalDate.now().minus(2, ChronoUnit.YEARS);
    private static final LocalDate DUE_DATE = LocalDate.now().plus(1, ChronoUnit.MONTHS);

    public static Batch createBatch(Integer batchNumber, Product product) {
        return Batch.builder()
                .batchNumber(batchNumber)
                .product(product)
                .initialQuantity(50)
                .manufacturingTime(MANUFACTURING_TIME)
                .manufacturingDate(MANUFACTURING_DATE)
                .dueDate(DUE_DATE)
                .currentQuantity(50)
                .section(SectionFactory.section1())
                .build();
    }

    public static Batch createBatchWithQuantityAndDueDate(Integer batchNumber, Product product, Integer quantity, LocalDate dueDate){
        return Batch.builder()
                .batchNumber(batchNumber)
                .product(product)
                .initialQuantity(50)
                .manufacturingTime(MANUFACTURING_TIME)
                .manufacturingDate(MANUFACTURING_DATE)
                .dueDate(dueDate)
                .currentQuantity(quantity)
                .section(SectionFactory.section1())
                .build();
    }

    public static List<Batch> createListOfBatches(Batch... batches) {
        return Arrays.asList(batches);
    }

    public static BatchDTO createBatchDTO(Integer batchNumber, Integer productId){
        return BatchDTO.builder()
                .batchNumber(batchNumber)
                .productId(productId)
                .initialQuantity(50)
                .currentTemperature(20D)
                .manufacturingTime(MANUFACTURING_TIME)
                .manufacturingDate(MANUFACTURING_DATE)
                .dueDate(DUE_DATE)
                .currentQuantity(50)
                .build();
    }

    public static BatchDTO createBatchDTOWithCustomQuantity(Integer batchNumber, Integer productId, Integer quantity){
        return BatchDTO.builder()
                .batchNumber(batchNumber)
                .productId(productId)
                .initialQuantity(quantity)
                .currentTemperature(20D)
                .manufacturingTime(MANUFACTURING_TIME)
                .manufacturingDate(MANUFACTURING_DATE)
                .dueDate(DUE_DATE)
                .currentQuantity(quantity)
                .build();
    }

    public static Batch createBatchWithCustomQuantity(Integer batchNumber, Product product, Integer initialQuantity, Section section, Integer currentQuantity) {
        return Batch.builder()
                .product(product)
                .batchNumber(batchNumber)
                .initialQuantity(initialQuantity)
                .manufacturingTime(MANUFACTURING_TIME)
                .manufacturingDate(MANUFACTURING_DATE)
                .dueDate(DUE_DATE)
                .currentQuantity(currentQuantity)
                .section(section)
                .build();
    }

    public static BatchDTO createBatchResponseDTO(Integer batchNumber, Integer productId) {
        return BatchDTO.builder()
                .batchNumber(batchNumber)
                .productId(productId)
                .initialQuantity(50)
                .currentTemperature(20D)
                .manufacturingTime(MANUFACTURING_TIME)
                .manufacturingDate(MANUFACTURING_DATE)
                .dueDate(DUE_DATE)
                .currentQuantity(50)
                .build();
    }

    public static List<BatchDTO> createListOfBatchesDTO(BatchDTO... batches) {
        return Arrays.asList(batches);
    }

    public static BatchStockResponseDTO createBatchStockResponseDTO(Integer productId, BatchBySectionDTO... batchBySectionDTOs) {
        return BatchStockResponseDTO.builder()
                .productId(productId)
                .batchs(Arrays.asList(batchBySectionDTOs))
                .build();
    }

    public static BatchBySectionDTO createBatchBySectionDTO(SectionDTO section, BatchSummaryDTO... batchSummaryDTOs) {
        return BatchBySectionDTO.builder()
                .section(section)
                .batchesPerSection(Arrays.asList(batchSummaryDTOs))
                .build();
    }

    public static BatchSummaryDTO createBatchSummaryDTOWithCustomQuantityAndDueDate(Integer batchNumber, Integer quantity, LocalDate dueDate) {
        return BatchSummaryDTO.builder()
                .batchNumber(batchNumber)
                .currentQuantity(quantity)
                .dueDate(dueDate)
                .build();
    }

    public static BatchStockDTO createBatchStockDTOWithBatches(BatchDTO batchDTOs) {
        return new BatchStockDTO(Arrays.asList(batchDTOs));
    }

    public static ProductCountByWarehouseDTO createProductCountByWarehouseDTO(Integer warehouseId, Integer count){
        return ProductCountByWarehouseDTO
                .builder()
                .productCount(warehouseId)
                .warehouseId(count)
                .build();
    }

    public static ProductCountsDTO createProductCountsDTO(ProductCountByWarehouseDTO... pcbwdto){
        return ProductCountsDTO.builder()
                .productCountByWarehouse(Arrays.asList(pcbwdto))
                .build();
    }

    public static BatchNearExpirationDTO createBatchNearExpirationDTO(Integer batchNumber, Integer currentQuantity, Integer productId, Type productType){
        return BatchNearExpirationDTO.builder()
                .batchNumber(batchNumber)
                .currentQuantity(currentQuantity)
                .productId(productId)
                .productTypeId(productType)
                .build();
    }

    public static BatchStockNearExpirationDTO createBatchStockNearExpirationDTO(BatchNearExpirationDTO... batches){
        return BatchStockNearExpirationDTO.builder()
                .batchStock(Arrays.asList(batches))
                .build();
    }
}