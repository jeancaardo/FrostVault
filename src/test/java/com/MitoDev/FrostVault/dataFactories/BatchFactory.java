package com.MitoDev.FrostVault.dataFactories;

import com.MitoDev.FrostVault.model.dto.BatchDTO;
import com.MitoDev.FrostVault.model.entity.Batch;
import com.MitoDev.FrostVault.model.entity.Section;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BatchFactory {

    public static Batch batchPersisted1(){
        return Batch.builder()
                .batchNumber(1)
                .product(ProductFactory.product1())
                .initialQuantity(25)
                .manufacturingTime(LocalDateTime.of(2020, 2, 20, 20, 20))
                .manufacturingDate(LocalDate.of(2020, 2, 20))
                .dueDate(LocalDate.of(2024, 2, 20))
                .currentQuantity(25)
                .section(SectionFactory.section1())
                .build();
    }

    public static Batch batchPersisted2(){
        return Batch.builder()
                .batchNumber(2)
                .product(ProductFactory.product1())
                .initialQuantity(50)
                .manufacturingTime(LocalDateTime.of(2020, 2, 20, 20, 20))
                .manufacturingDate(LocalDate.of(2020, 2, 20))
                .dueDate(LocalDate.of(2024, 2, 20))
                .currentQuantity(50)
                .section(SectionFactory.section1())
                .build();
    }


    public static Batch batchNew1(){
        return Batch.builder()
                .product(ProductFactory.product1())
                .initialQuantity(25)
                .manufacturingTime(LocalDateTime.of(2020, 2, 20, 20, 20))
                .manufacturingDate(LocalDate.of(2020, 2, 20))
                .dueDate(LocalDate.of(2024, 2, 20))
                .currentQuantity(25)
                .section(SectionFactory.section1())
                .build();
    }

    public static Batch batchNew2(){
        return Batch.builder()
                .product(ProductFactory.product1())
                .initialQuantity(50)
                .manufacturingTime(LocalDateTime.of(2020, 2, 20, 20, 20))
                .manufacturingDate(LocalDate.of(2020, 2, 20))
                .dueDate(LocalDate.of(2024, 2, 20))
                .currentQuantity(50)
                .section(SectionFactory.section1())
                .build();
    }
    public static List<Batch> listOfBatchesNew(){
        return List.of(batchNew1());
    }

    public static List<Batch> listOfBatchesWithOnePersistedAndOneNew(){
        return List.of(batchPersisted1(), batchNew2());
    }

    public static List<Batch> listOfBatchesPersisted(){
        return List.of(batchPersisted1());
    }
    public static List<Batch> listOfBatchesPersisted2(){
        return List.of(batchPersisted1(), batchPersisted2());
    }

    public static List<Batch> listOfBatchPersisted2WithTooMuchQuantity(){
        return List.of(batchPersisted1(), batchPersisted2());
    }
    public static List<Batch> listOfBatchesPersistedToUpdate(){
        return List.of(batchPersisted1(), batchNew2());
    }
    public static BatchDTO batchRequestDTO1(){
        return BatchDTO.builder()
                .productId(1)
                .initialQuantity(25)
                .currentTemperature(20D)
                .manufacturingTime(LocalDateTime.of(2020, 2, 20, 20, 20))
                .manufacturingDate(LocalDate.of(2020, 2, 20))
                .dueDate(LocalDate.of(2024, 2, 20))
                .currentQuantity(25)
                .build();
    }

    public static BatchDTO batchRequestExistentDTO1(){
        return BatchDTO.builder()
                .batchNumber(1)
                .productId(1)
                .initialQuantity(25)
                .currentTemperature(20D)
                .manufacturingTime(LocalDateTime.of(2020, 2, 20, 20, 20))
                .manufacturingDate(LocalDate.of(2020, 2, 20))
                .dueDate(LocalDate.of(2024, 2, 20))
                .currentQuantity(25)
                .build();
    }

    public static BatchDTO batchRequestDTO2(){
        return BatchDTO.builder()
                .productId(1)
                .initialQuantity(50)
                .currentTemperature(20D)
                .manufacturingTime(LocalDateTime.of(2020, 2, 20, 20, 20))
                .manufacturingDate(LocalDate.of(2020, 2, 20))
                .dueDate(LocalDate.of(2024, 2, 20))
                .currentQuantity(50)
                .build();
    }

    public static BatchDTO batchRequestDTO2WithTooMuchQuantity(){
        return BatchDTO.builder()
                .productId(2)
                .initialQuantity(5000)
                .currentTemperature(20D)
                .manufacturingTime(LocalDateTime.of(2020, 2, 20, 20, 20))
                .manufacturingDate(LocalDate.of(2020, 2, 20))
                .dueDate(LocalDate.of(2024, 2, 20))
                .currentQuantity(5000)
                .build();
    }

    public static BatchDTO batchResponseDTO1(){
        return BatchDTO.builder()
                .batchNumber(1)
                .productId(1)
                .initialQuantity(25)
                .currentTemperature(20D)
                .manufacturingTime(LocalDateTime.of(2020, 2, 20, 20, 20))
                .manufacturingDate(LocalDate.of(2020, 2, 20))
                .dueDate(LocalDate.of(2024, 2, 20))
                .currentQuantity(25)
                .build();
    }

    public static BatchDTO batchResponseDTO2(){
        return BatchDTO.builder()
                .batchNumber(2)
                .productId(1)
                .initialQuantity(50)
                .currentTemperature(20D)
                .manufacturingTime(LocalDateTime.of(2020, 2, 20, 20, 20))
                .manufacturingDate(LocalDate.of(2020, 2, 20))
                .dueDate(LocalDate.of(2024, 2, 20))
                .currentQuantity(50)
                .build();
    }

    public static List<BatchDTO> listOfBatchesRequestDTO1(){
        return List.of(batchRequestDTO1());
    }
    public static List<BatchDTO> listOfBatchesRequestDTO2(){
        return List.of(batchRequestExistentDTO1(), batchRequestDTO2());
    }

    public static List<BatchDTO> listOfBatchesRequestDTO2WithBatchesTooMuchQuantity(){
        return List.of(batchRequestDTO1(), batchRequestDTO2WithTooMuchQuantity());
    }

    public static List<BatchDTO> listOfBatchesResponseDTO(){
        return List.of(batchResponseDTO1());
    }

    public static List<BatchDTO> listOfBatchesResponseDTO2(){
        return List.of(batchResponseDTO1(), batchResponseDTO2());
    }
}
