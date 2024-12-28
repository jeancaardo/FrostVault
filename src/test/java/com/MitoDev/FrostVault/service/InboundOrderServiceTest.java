package com.MitoDev.FrostVault.service;

import com.MitoDev.FrostVault.dataFactories.*;
import com.MitoDev.FrostVault.exception.custom.EntityDoesNotExistException;
import com.MitoDev.FrostVault.exception.custom.NotEnoughSectionCapacityException;
import com.MitoDev.FrostVault.exception.custom.SectionAndProductTypeDoesNotMatch;
import com.MitoDev.FrostVault.exception.custom.UserNotBelongsToWarehouseException;
import com.MitoDev.FrostVault.model.dto.BatchDTO;
import com.MitoDev.FrostVault.model.dto.BatchStockDTO;
import com.MitoDev.FrostVault.model.dto.InboundOrderRequestDTO;
import com.MitoDev.FrostVault.model.dto.SectionDTO;
import com.MitoDev.FrostVault.model.entity.*;
import com.MitoDev.FrostVault.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InboundOrderServiceTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    IInboundOrderRepository inboundOrderRepository;
    @Mock
    IBatchRepository batchRepository;
    @Mock
    IUserRepository userRepository;
    @Mock
    ISectionRepository sectionRepository;
    @Mock
    IProductRepository productRepository;

    @InjectMocks
    InboundOrderService service;

    private final User user = UserFactory.user1();
    
    private final Section section1 = SectionFactory.section1();

    private final SectionDTO sectionDTO1 = SectionFactory.sectionDTO1();
    
    private final Product product1 = ProductFactory.product1();

    private final Product product2 = ProductFactory.product2();
    
    private final Batch newBatch1 = BatchFactory.createBatch(null, product1);

    private final Batch newBatch2 = BatchFactory.createBatch(null, product1);

    private final BatchDTO newBatchDTO1 = BatchFactory.createBatchDTO(null, 1);

    private final BatchDTO newBatchDTO1WithTooMuchQuantity = BatchFactory.createBatchDTOWithCustomQuantity(null, 1, 5000);

    private final BatchDTO newBatchDTO2 = BatchFactory.createBatchDTO(null, 2);

    private final Batch persistedBatch1 = BatchFactory.createBatch(1, product1);
    private final Batch persistedBatch2 = BatchFactory.createBatch(2, product1);

    private final Batch persistedBatchWith0CurrentCapacity = BatchFactory.createBatchWithCustomQuantity(1, product1, 50, section1, 0);
    private final Batch persistedBatchWithNotEnoughCapacity = BatchFactory.createBatchWithCustomQuantity(1, product1, 50, section1, 1);
    private final BatchDTO persistedBatchDTO1 = BatchFactory.createBatchDTO(1, 1);

    private final BatchDTO persistedBatchDTO2 = BatchFactory.createBatchDTO(2, 1);

    @BeforeEach()
    void setup() throws JsonProcessingException {
        Authentication authentication = Mockito.mock(Authentication.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication().getPrincipal()).thenReturn(objectMapper.writeValueAsString(user));
    }

    @Test
    @DirtiesContext
    @DisplayName("Should add a new inbound order and return a list of batches")
    void addNewInboundOrderTest() {
        // arrange
        InboundOrderRequestDTO dto = InboundOrderFactory.createInboundOrderRequestDTO(null, sectionDTO1, newBatchDTO1);

        var expected = BatchFactory.createBatchStockDTOWithBatches(BatchFactory.createBatchResponseDTO(1, 1));
        // act

        when(userRepository.findByIdEqualsAndWarehouseIdEquals(user.getId(), user.getWarehouse().getId())).thenReturn(Optional.of(user));
        when(sectionRepository.findById(dto.getSection().getSectionCode())).thenReturn(Optional.of(section1));
        when(productRepository.findById(any())).thenReturn(Optional.of(product1));
        when(batchRepository.findBySectionSectionCodeEquals(dto.getSection().getSectionCode())).thenReturn(List.of());
        when(batchRepository.saveAll(BatchFactory.createListOfBatches(newBatch1))).thenReturn(BatchFactory.createListOfBatches(persistedBatch1));
        when(inboundOrderRepository.save(InboundOrderFactory.createInboundOrder(null, section1, persistedBatch1)))
                .thenReturn(InboundOrderFactory.createInboundOrder(1, section1, persistedBatch1));

        var obtained = service.addNewInboundOrder(dto);

        // assert

        Assertions.assertEquals(expected, obtained);
    }

    @Test
    @DisplayName("Should throw error user does not belong to warehouse")
    void addNewInboundOrderTest2()  {
        // arrange
        InboundOrderRequestDTO dto = InboundOrderFactory.createInboundOrderRequestDTO(null, sectionDTO1, newBatchDTO1);
        // act

        when(userRepository.findByIdEqualsAndWarehouseIdEquals(user.getId(), user.getWarehouse().getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotBelongsToWarehouseException.class, ()-> {service.addNewInboundOrder(dto);} );
    }

    @Test
    @DisplayName("Should throw error section does not exist")
    void addNewInboundOrderTest3()  {
        // arrange
        InboundOrderRequestDTO dto = InboundOrderFactory.createInboundOrderRequestDTO(null, sectionDTO1, newBatchDTO1);
        // act
        when(userRepository.findByIdEqualsAndWarehouseIdEquals(user.getId(), user.getWarehouse().getId())).thenReturn(Optional.of(user));
        when(sectionRepository.findById(dto.getSection().getSectionCode())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityDoesNotExistException.class, ()-> {service.addNewInboundOrder(dto);} );
    }

    @Test
    @DisplayName("Should throw an exception because of product not existing")
    void addNewInboundOrderTest4()  {
        // arrange
        InboundOrderRequestDTO dto = InboundOrderFactory.createInboundOrderRequestDTO(null, sectionDTO1, newBatchDTO1);
        // act

        when(userRepository.findByIdEqualsAndWarehouseIdEquals(user.getId(), user.getWarehouse().getId())).thenReturn(Optional.of(user));
        when(sectionRepository.findById(dto.getSection().getSectionCode())).thenReturn(Optional.of(section1));
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityDoesNotExistException.class, ()-> {service.addNewInboundOrder(dto);} );
    }

    @Test
    @DisplayName("Should throw an exception because of different types of product and section")
    void addNewInboundOrderTest5()  {
        // arrange
        InboundOrderRequestDTO dto = InboundOrderFactory.createInboundOrderRequestDTO(null, sectionDTO1, newBatchDTO1);
        // act

        when(userRepository.findByIdEqualsAndWarehouseIdEquals(user.getId(), user.getWarehouse().getId())).thenReturn(Optional.of(user));
        when(sectionRepository.findById(dto.getSection().getSectionCode())).thenReturn(Optional.of(section1));
        when(productRepository.findById(any())).thenReturn(Optional.of(product2));

        Assertions.assertThrows(SectionAndProductTypeDoesNotMatch.class, ()-> {service.addNewInboundOrder(dto);} );
    }

    @Test
    @DisplayName("Should throw an exception because of not enough capacity in section")
    void addNewInboundOrderTest6() {
        // arrange
        InboundOrderRequestDTO dto = InboundOrderFactory.createInboundOrderRequestDTO(null, sectionDTO1, newBatchDTO1WithTooMuchQuantity);
        // act

        when(userRepository.findByIdEqualsAndWarehouseIdEquals(user.getId(), user.getWarehouse().getId())).thenReturn(Optional.of(user));
        when(sectionRepository.findById(dto.getSection().getSectionCode())).thenReturn(Optional.of(section1));
        when(productRepository.findById(any())).thenReturn(Optional.of(product1));
        when(batchRepository.findBySectionSectionCodeEquals(dto.getSection().getSectionCode())).thenReturn(List.of(persistedBatch1));


        Assertions.assertThrows(NotEnoughSectionCapacityException.class, ()-> {service.addNewInboundOrder(dto);} );
    }

    @Test
    @DirtiesContext
    @DisplayName("Should update a given id inbound order and return a list of batches")
    void updateNewInboundOrderTest1() {
        // arrange
        InboundOrderRequestDTO dto = InboundOrderFactory.createInboundOrderRequestDTO(null, sectionDTO1, newBatchDTO1);
        var expected = new BatchStockDTO(BatchFactory.createListOfBatchesDTO(persistedBatchDTO1, persistedBatchDTO2));
        // act

        when(inboundOrderRepository.findById(dto.getOrderNumber())).thenReturn(Optional.of(InboundOrderFactory.createInboundOrder(1, section1, persistedBatch1)));
        when(userRepository.findByIdEqualsAndWarehouseIdEquals(user.getId(), user.getWarehouse().getId())).thenReturn(Optional.of(user));
        when(sectionRepository.findById(dto.getSection().getSectionCode())).thenReturn(Optional.of(section1));
        when(productRepository.findById(any())).thenReturn(Optional.of(product1));
        when(batchRepository.findBySectionSectionCodeEquals(dto.getSection().getSectionCode())).thenReturn(BatchFactory.createListOfBatches(persistedBatch1));
        when(batchRepository.saveAll(BatchFactory.createListOfBatches(persistedBatch1, newBatch2))).thenReturn(BatchFactory.createListOfBatches(persistedBatch1, persistedBatch2));
        when(inboundOrderRepository.save(InboundOrderFactory.createInboundOrder(null, section1, persistedBatch1, persistedBatch2))).thenReturn(InboundOrderFactory.createInboundOrder(1, section1, persistedBatch1, persistedBatch2));


        var obtained = service.updateNewInboundOrder(dto);

        // assert

        Assertions.assertEquals(expected, obtained);
    }
}
