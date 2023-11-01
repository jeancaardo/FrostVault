package com.MitoDev.FrostVault.service;

import com.MitoDev.FrostVault.dataFactories.*;
import com.MitoDev.FrostVault.exception.custom.EntityDoesNotExistException;
import com.MitoDev.FrostVault.exception.custom.NotEnoughSectionCapacityException;
import com.MitoDev.FrostVault.exception.custom.SectionAndProductTypeDoesNotMatch;
import com.MitoDev.FrostVault.exception.custom.UserNotBelongsToWarehouseException;
import com.MitoDev.FrostVault.model.dto.BatchStockDTO;
import com.MitoDev.FrostVault.model.dto.InboundOrderRequestDTO;
import com.MitoDev.FrostVault.model.entity.Batch;
import com.MitoDev.FrostVault.model.entity.InboundOrder;
import com.MitoDev.FrostVault.model.entity.Product;
import com.MitoDev.FrostVault.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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

    @Test
    @DirtiesContext
    @DisplayName("Should add a new inbound order and return a list of batches")
    void addNewInboundOrderTest() throws JsonProcessingException {
        // arrange
        InboundOrderRequestDTO dto = InboundOrderFactory.inboundOrderRequestDTO1();
        var expected = new BatchStockDTO(BatchFactory.listOfBatchesResponseDTO());
        // act

        Authentication authentication = Mockito.mock(Authentication.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication().getPrincipal()).thenReturn(objectMapper.writeValueAsString(UserFactory.user1()));
        when(userRepository.findByIdEqualsAndWarehouseIdEquals(UserFactory.user1().getId(), UserFactory.user1().getWarehouse().getId())).thenReturn(Optional.of(UserFactory.user1()));
        when(sectionRepository.findById(dto.getSection().getSectionCode())).thenReturn(Optional.of(SectionFactory.section1()));
        when(productRepository.findById(any())).thenReturn(Optional.of(ProductFactory.product1()));
        when(batchRepository.findBySectionSectionCodeEquals(dto.getSection().getSectionCode())).thenReturn(List.of());
        when(batchRepository.saveAll(BatchFactory.listOfBatchesNew())).thenReturn(BatchFactory.listOfBatchesPersisted());
        when(inboundOrderRepository.save(InboundOrderFactory.inboundOrderNew1())).thenReturn(InboundOrderFactory.inboundOrderPersisted1());

        var obtained = service.addNewInboundOrder(dto);

        // assert

        Assertions.assertEquals(expected, obtained);
    }

    @Test
    @DisplayName("Should throw error user does not belong to warehouse")
    void addNewInboundOrderTest2() throws JsonProcessingException {
        // arrange
        InboundOrderRequestDTO dto = InboundOrderFactory.inboundOrderRequestDTO1();
        var expected = new BatchStockDTO(BatchFactory.listOfBatchesResponseDTO());
        // act

        Authentication authentication = Mockito.mock(Authentication.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication().getPrincipal()).thenReturn(objectMapper.writeValueAsString(UserFactory.user1()));
        when(userRepository.findByIdEqualsAndWarehouseIdEquals(UserFactory.user1().getId(), UserFactory.user1().getWarehouse().getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotBelongsToWarehouseException.class, ()-> {service.addNewInboundOrder(dto);} );
    }

    @Test
    @DisplayName("Should throw error section does not exist")
    void addNewInboundOrderTest3() throws JsonProcessingException {
        // arrange
        InboundOrderRequestDTO dto = InboundOrderFactory.inboundOrderRequestDTO1();
        var expected = new BatchStockDTO(BatchFactory.listOfBatchesResponseDTO());
        // act

        Authentication authentication = Mockito.mock(Authentication.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication().getPrincipal()).thenReturn(objectMapper.writeValueAsString(UserFactory.user1()));
        when(userRepository.findByIdEqualsAndWarehouseIdEquals(UserFactory.user1().getId(), UserFactory.user1().getWarehouse().getId())).thenReturn(Optional.of(UserFactory.user1()));
        when(sectionRepository.findById(dto.getSection().getSectionCode())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityDoesNotExistException.class, ()-> {service.addNewInboundOrder(dto);} );
    }

    @Test
    @DisplayName("Should throw an exception because of product not existing")
    void addNewInboundOrderTest4() throws JsonProcessingException {
        // arrange
        InboundOrderRequestDTO dto = InboundOrderFactory.inboundOrderRequestDTO1();
        // act

        Authentication authentication = Mockito.mock(Authentication.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication().getPrincipal()).thenReturn(objectMapper.writeValueAsString(UserFactory.user1()));
        when(userRepository.findByIdEqualsAndWarehouseIdEquals(UserFactory.user1().getId(), UserFactory.user1().getWarehouse().getId())).thenReturn(Optional.of(UserFactory.user1()));
        when(sectionRepository.findById(dto.getSection().getSectionCode())).thenReturn(Optional.of(SectionFactory.section1()));
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityDoesNotExistException.class, ()-> {service.addNewInboundOrder(dto);} );
    }

    @Test
    @DisplayName("Should throw an exception because of different types of product and section")
    void addNewInboundOrderTest5() throws JsonProcessingException {
        // arrange
        InboundOrderRequestDTO dto = InboundOrderFactory.inboundOrderRequestDTO1();
        // act

        Authentication authentication = Mockito.mock(Authentication.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication().getPrincipal()).thenReturn(objectMapper.writeValueAsString(UserFactory.user1()));
        when(userRepository.findByIdEqualsAndWarehouseIdEquals(UserFactory.user1().getId(), UserFactory.user1().getWarehouse().getId())).thenReturn(Optional.of(UserFactory.user1()));
        when(sectionRepository.findById(dto.getSection().getSectionCode())).thenReturn(Optional.of(SectionFactory.section1()));
        when(productRepository.findById(any())).thenReturn(Optional.of(ProductFactory.product2()));

        Assertions.assertThrows(SectionAndProductTypeDoesNotMatch.class, ()-> {service.addNewInboundOrder(dto);} );
    }

    @Test
    @DisplayName("Should throw an exception because of not enough capacity in section")
    void addNewInboundOrderTest6() throws JsonProcessingException {
        // arrange
        InboundOrderRequestDTO dto = InboundOrderFactory.inboundOrderRequestExistentDTOWithTooMuchQuantity();
        // act

        Authentication authentication = Mockito.mock(Authentication.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication().getPrincipal()).thenReturn(objectMapper.writeValueAsString(UserFactory.user1()));
        when(userRepository.findByIdEqualsAndWarehouseIdEquals(UserFactory.user1().getId(), UserFactory.user1().getWarehouse().getId())).thenReturn(Optional.of(UserFactory.user1()));
        when(sectionRepository.findById(dto.getSection().getSectionCode())).thenReturn(Optional.of(SectionFactory.section1()));
        when(productRepository.findById(any())).thenReturn(Optional.of(ProductFactory.product1()));
        when(batchRepository.findBySectionSectionCodeEquals(dto.getSection().getSectionCode())).thenReturn(List.of(BatchFactory.batchPersisted2()));


        Assertions.assertThrows(NotEnoughSectionCapacityException.class, ()-> {service.addNewInboundOrder(dto);} );
    }

    @Test
    @DirtiesContext
    @DisplayName("Should update a given id inbound order and return a list of batches")
    void updateNewInboundOrderTest1() throws JsonProcessingException {
        // arrange
        InboundOrderRequestDTO dto = InboundOrderFactory.inboundOrderRequestExistentDTO1();
        var expected = new BatchStockDTO(BatchFactory.listOfBatchesResponseDTO2());
        // act
        Authentication authentication = Mockito.mock(Authentication.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication().getPrincipal()).thenReturn(objectMapper.writeValueAsString(UserFactory.user1()));

        when(inboundOrderRepository.findById(dto.getOrderNumber())).thenReturn(Optional.of(InboundOrderFactory.inboundOrderPersisted1()));
        when(userRepository.findByIdEqualsAndWarehouseIdEquals(UserFactory.user1().getId(), UserFactory.user1().getWarehouse().getId())).thenReturn(Optional.of(UserFactory.user1()));
        when(sectionRepository.findById(dto.getSection().getSectionCode())).thenReturn(Optional.of(SectionFactory.section1()));
        when(productRepository.findById(any())).thenReturn(Optional.of(ProductFactory.product1()));
        when(batchRepository.findBySectionSectionCodeEquals(dto.getSection().getSectionCode())).thenReturn(BatchFactory.listOfBatchesPersisted());
        when(batchRepository.saveAll(BatchFactory.listOfBatchesWithOnePersistedAndOneNew())).thenReturn(BatchFactory.listOfBatchesPersisted2());
        when(inboundOrderRepository.save(InboundOrderFactory.inboundOrderUpdated1())).thenReturn(InboundOrderFactory.inboundOrderToUpdateWithBatchesSaved());


        var obtained = service.updateNewInboundOrder(dto);

        // assert

        Assertions.assertEquals(expected, obtained);
    }
}
