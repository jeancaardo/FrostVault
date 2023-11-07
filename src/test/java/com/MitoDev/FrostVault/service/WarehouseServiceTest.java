package com.MitoDev.FrostVault.service;

import com.MitoDev.FrostVault.dataFactories.BatchFactory;
import com.MitoDev.FrostVault.exception.custom.NoProductsException;
import com.MitoDev.FrostVault.repository.IBatchRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WarehouseServiceTest {

    @Mock
    IBatchRepository batchRepository;

    @InjectMocks
    WarehouseService warehouseService;

    @Test
    void getProductCountAtEachWarehouse() {
        // Arrange
        Integer productId = 1;
        var expected = BatchFactory.createProductCountsDTO(BatchFactory.createProductCountByWarehouseDTO(1, 50));
        // Act
        when(batchRepository.getProductCountByWarehouse(productId)).thenReturn(List.of(BatchFactory.createProductCountByWarehouseDTO(1, 50)));
        var obtained = warehouseService.getProductCountAtEachWarehouse(1);

        // Assert
        Assertions.assertEquals(expected, obtained);
    }

    @Test
    void getProductCountAtEachWarehouseError() {
        // Arrange
        Integer productId = 1;
        // Act
        when(batchRepository.getProductCountByWarehouse(productId)).thenReturn(List.of());

        // Assert
        Assertions.assertThrows(NoProductsException.class, () -> warehouseService.getProductCountAtEachWarehouse(1));
    }
}