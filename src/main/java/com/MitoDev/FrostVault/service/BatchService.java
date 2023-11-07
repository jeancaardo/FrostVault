package com.MitoDev.FrostVault.service;

import com.MitoDev.FrostVault.exception.custom.EntityDoesNotExistException;
import com.MitoDev.FrostVault.exception.custom.NoProductsException;
import com.MitoDev.FrostVault.model.dto.*;
import com.MitoDev.FrostVault.model.entity.Batch;
import com.MitoDev.FrostVault.model.entity.Product;
import com.MitoDev.FrostVault.model.entity.Section;
import com.MitoDev.FrostVault.repository.IBatchRepository;
import com.MitoDev.FrostVault.repository.IProductRepository;
import com.MitoDev.FrostVault.service.interfaces.IBatchService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BatchService implements IBatchService {

    private final IBatchRepository batchRepository;

    private final IProductRepository productRepository;

    public BatchService(IBatchRepository batchRepository, IProductRepository productRepository) {
        this.batchRepository = batchRepository;
        this.productRepository = productRepository;
    }

    @Override
    public BatchStockResponseDTO getBatchesByProduct(Integer productId) {

        if(!productRepository.existsById(productId))
            throw new EntityDoesNotExistException(Product.class, productId);

        var batches = batchRepository.findByProductIdProductEquals(productId);

        if(batches.isEmpty())
            throw new NoProductsException();

        Map<Section, List<Batch>> batchesPerSection = groupBatchesBySection(batches);

        return createBatchStockResponse(productId, batchesPerSection);
    }

    @Override
    public BatchStockResponseDTO getBatchesByProductOrdered(Integer productId, Character order) {

        if (!productRepository.existsById(productId)) {
            throw new EntityDoesNotExistException(Product.class, productId);
        }

        List<Batch> batches = batchRepository.findByProductIdProductEquals(productId);

        Comparator<Batch> comparator = getBatchComparator(order);

        Map<Section, List<Batch>> batchesPerSection = groupBatchesBySection(batches, comparator);

        return createBatchStockResponse(productId, batchesPerSection);
    }

    private Comparator<Batch> getBatchComparator(char order) {
        switch (order) {
            case 'L':
                return Comparator.comparing(Batch::getBatchNumber);
            case 'C':
                return Comparator.comparing(Batch::getCurrentQuantity);
            case 'F':
                return Comparator.comparing(Batch::getDueDate);
            default:
                throw new RuntimeException("Orden no válida");
        }
    }

    private Map<Section, List<Batch>> groupBatchesBySection(List<Batch> batches, Comparator<Batch> comparator) {
        return batches.stream()
                .sorted(comparator)
                .collect(Collectors.groupingBy(Batch::getSection));
    }

    private Map<Section, List<Batch>> groupBatchesBySection(List<Batch> batches) {
        return batches.stream()
                .collect(Collectors.groupingBy(Batch::getSection));
    }

    private BatchStockResponseDTO createBatchStockResponse(Integer productId, Map<Section, List<Batch>> batchesPerSection) {
        return BatchStockResponseDTO.builder()
                .productId(productId)
                .batchs(
                        batchesPerSection.entrySet().stream()
                                .map(entry -> createBatchBySectionDTO(entry.getKey(), entry.getValue()))
                                .collect(Collectors.toList())
                )
                .build();
    }

    private BatchBySectionDTO createBatchBySectionDTO(Section section, List<Batch> batches) {
        return BatchBySectionDTO.builder()
                .section(
                        SectionDTO.builder()
                                .warehouseCode(section.getWarehouse().getId())
                                .sectionCode(section.getSectionCode())
                                .build()
                )
                .batchesPerSection(
                        batches.stream()
                                .map(this::createBatchSummaryDTO)
                                .collect(Collectors.toList())
                )
                .build();
    }

    private BatchSummaryDTO createBatchSummaryDTO(Batch batch) {
        return BatchSummaryDTO.builder()
                .batchNumber(batch.getBatchNumber())
                .currentQuantity(batch.getCurrentQuantity())
                .dueDate(batch.getDueDate())
                .build();
    }
}