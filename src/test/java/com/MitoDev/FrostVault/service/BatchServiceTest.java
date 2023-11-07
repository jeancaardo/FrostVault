package com.MitoDev.FrostVault.service;

import com.MitoDev.FrostVault.dataFactories.BatchFactory;
import com.MitoDev.FrostVault.dataFactories.ProductFactory;
import com.MitoDev.FrostVault.dataFactories.SectionFactory;
import com.MitoDev.FrostVault.exception.custom.EntityDoesNotExistException;
import com.MitoDev.FrostVault.exception.custom.NoProductsException;
import com.MitoDev.FrostVault.model.dto.BatchBySectionDTO;
import com.MitoDev.FrostVault.model.dto.BatchStockResponseDTO;
import com.MitoDev.FrostVault.model.dto.BatchSummaryDTO;
import com.MitoDev.FrostVault.model.dto.SectionDTO;
import com.MitoDev.FrostVault.model.entity.Batch;
import com.MitoDev.FrostVault.model.entity.Product;
import com.MitoDev.FrostVault.model.entity.Section;
import com.MitoDev.FrostVault.repository.IBatchRepository;
import com.MitoDev.FrostVault.repository.IProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BatchServiceTest {

    @Mock
    IBatchRepository batchRepository;

    @Mock
    IProductRepository productRepository;

    @InjectMocks
    BatchService batchService;

    private final Product product1 = ProductFactory.product1();

    private final Batch persistedBatch1 = BatchFactory.createBatchWithQuantityAndDueDate(1, product1, 40, LocalDate.now().minus(6, ChronoUnit.MONTHS));

    private final Batch persistedBatch2 = BatchFactory.createBatchWithQuantityAndDueDate(2, product1, 60, LocalDate.now().minus(4, ChronoUnit.MONTHS));

    private final SectionDTO section1 = SectionFactory.sectionDTO1();

    private final BatchSummaryDTO batchSummaryDTO1 = BatchFactory.createBatchSummaryDTOWithCustomQuantityAndDueDate(1, 40, LocalDate.now().minus(6, ChronoUnit.MONTHS));

    private final BatchSummaryDTO batchSummaryDTO2 = BatchFactory.createBatchSummaryDTOWithCustomQuantityAndDueDate(2, 60, LocalDate.now().minus(4, ChronoUnit.MONTHS));

    private final BatchBySectionDTO batchBySectionDTO1 = BatchFactory.createBatchBySectionDTO(section1, batchSummaryDTO1, batchSummaryDTO2);

    private final BatchStockResponseDTO batchStockResponseDTO = BatchFactory.createBatchStockResponseDTO(1, batchBySectionDTO1);


    @Test
    void getBatchesByProduct() {
        // arrange
        Integer productId = 1;
        var expected = batchStockResponseDTO;
        // act
        when(productRepository.existsById(productId)).thenReturn(true);
        when(batchRepository.findByProductIdProductEquals(productId))
                .thenReturn(BatchFactory.createListOfBatches(persistedBatch1, persistedBatch2));

        var obtained = batchService.getBatchesByProduct(productId);
        // assert

        Assertions.assertEquals(expected, obtained);
    }

    @Test
    @DisplayName("Should return error because product does not exist")
    void getBatchesByProduct2() {
        // arrange
        Integer productId = 10000;
        var expected = BatchFactory.createListOfBatches(persistedBatch1, persistedBatch2);
        // act
        when(productRepository.existsById(productId)).thenReturn(false);

        // assert
        Assertions.assertThrows(EntityDoesNotExistException.class, ()-> batchService.getBatchesByProduct(productId));
    }

    @Test
    void getBatchesByProduct3() {
        // arrange
        Integer productId = 1;
        var expected = BatchFactory.createListOfBatches(persistedBatch1, persistedBatch2);
        // act
        when(productRepository.existsById(productId)).thenReturn(true);
        when(batchRepository.findByProductIdProductEquals(productId)).thenReturn(List.of());
        // assert
        Assertions.assertThrows(NoProductsException.class, ()-> batchService.getBatchesByProduct(productId));

    }

    @Test
    void getBatchesByProductOrderedById() {
        // arrange
        Integer productId = 1;
        Character order = 'L';
        var expected = batchStockResponseDTO;
        // act
        when(productRepository.existsById(productId)).thenReturn(true);
        when(batchRepository.findByProductIdProductEquals(productId))
                .thenReturn(BatchFactory.createListOfBatches(persistedBatch1, persistedBatch2));

        var obtained = batchService.getBatchesByProductOrdered(productId, order);
        // assert

        Assertions.assertEquals(expected, obtained);
    }

    @Test
    void getBatchesByProductOrderedByQuantity() {
        // arrange
        Integer productId = 1;
        Character order = 'C';
        var expected = batchStockResponseDTO;
        // act
        when(productRepository.existsById(productId)).thenReturn(true);
        when(batchRepository.findByProductIdProductEquals(productId))
                .thenReturn(BatchFactory.createListOfBatches(persistedBatch1, persistedBatch2));

        var obtained = batchService.getBatchesByProductOrdered(productId, order);
        // assert

        Assertions.assertEquals(expected, obtained);
    }

    @Test
    void getBatchesByProductOrderedByDueDate() {
        // arrange
        Integer productId = 1;
        Character order = 'F';
        var expected = batchStockResponseDTO;
        // act
        when(productRepository.existsById(productId)).thenReturn(true);
        when(batchRepository.findByProductIdProductEquals(productId))
                .thenReturn(BatchFactory.createListOfBatches(persistedBatch1, persistedBatch2));

        var obtained = batchService.getBatchesByProductOrdered(productId, order);
        // assert

        Assertions.assertEquals(expected, obtained);
    }
}
