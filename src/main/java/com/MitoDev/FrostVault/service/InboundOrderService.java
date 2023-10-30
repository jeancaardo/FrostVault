package com.MitoDev.FrostVault.service;

import com.MitoDev.FrostVault.exception.custom.*;
import com.MitoDev.FrostVault.model.dto.BatchDTO;
import com.MitoDev.FrostVault.model.dto.BatchStockDTO;
import com.MitoDev.FrostVault.model.dto.InboundOrderRequestDTO;
import com.MitoDev.FrostVault.model.entity.*;
import com.MitoDev.FrostVault.repository.*;
import com.MitoDev.FrostVault.service.interfaces.IInboundOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InboundOrderService implements IInboundOrderService {

    ObjectMapper objectMapper = new ObjectMapper();

    private final IInboundOrderRepository inboundOrderRepository;
    private final IBatchRepository batchRepository;

    private final IUserRepository userRepository;

    private final ISectionRepository sectionRepository;

    private final IProductRepository productRepository;

    public InboundOrderService(IInboundOrderRepository inboundOrderRepository, IBatchRepository batchRepository, IUserRepository userRepository, ISectionRepository sectionRepository, IProductRepository productRepository) {
        this.inboundOrderRepository = inboundOrderRepository;
        this.batchRepository = batchRepository;
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.productRepository = productRepository;
    }

    @Override
    public BatchStockDTO addNewInboundOrder(InboundOrderRequestDTO req) throws JsonProcessingException {
        Section section = inboundOrderValidations(req);

        // convert dto to entity
        InboundOrder order = fromDtoToEntity(req, section);

        // TODO : CHECK IF WE CAN UPDATE BATCHES OR THEY STAY THE SAME.
        List<Batch> newBatchesPersisted = order.getBatches().stream().map(batchRepository::save).collect(Collectors.toList());
        order.setBatches(newBatchesPersisted);
        // persisting entity
        order = inboundOrderRepository.save(order);
        return new BatchStockDTO(order.getBatches().stream().map(
                this::fromEntityToDto
        ).toList());
    }

    @Override
    public BatchStockDTO updateNewInboundOrder(InboundOrderRequestDTO req) throws JsonProcessingException {
        // Assuring inbound order exists
        InboundOrder existentOrder = inboundOrderRepository.findById(req.getOrderNumber())
                .orElseThrow(
                        () ->{ throw new EntityDoesNotExistException(Section.class, req.getOrderNumber());}
                );

        Section section = inboundOrderValidations(req);

        // Use of design pattern builder for better understanding
        InboundOrder newOrder = fromDtoToEntity(req, section);
        ArrayList<Batch> newOrderBatches = new ArrayList<>(newOrder.getBatches());
        for (var batch : existentOrder.getBatches()) {
            if(newOrder.getBatches().stream().noneMatch(b -> b.getBatchNumber() == null || b.getBatchNumber().equals(batch.getBatchNumber())))
                newOrderBatches.add(batch);
        }

        newOrder.setBatches(newOrderBatches);
        List<Batch> batchesPersisted = newOrder.getBatches().stream().map(batchRepository::save).collect(Collectors.toList());
        newOrder.setBatches(batchesPersisted);

        // TODO : CHECK IF WE CAN UPDATE BATCHES OR THEY STAY THE SAME.

        newOrder = inboundOrderRepository.save(newOrder);
        return new BatchStockDTO(newOrder.getBatches().stream().map(
                this::fromEntityToDto
        ).toList());
    }

    private Section inboundOrderValidations(InboundOrderRequestDTO inboundOrder) throws JsonProcessingException {

        // Retrieving context data about user who requested
        User user = objectMapper.readValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), User.class);

        /// Check if admin belongs to this warehouse
        userRepository.findByIdEqualsAndWarehouseIdEquals(user.getId(), user.getWarehouse().getId()).orElseThrow(
                () -> { throw new UserNotBelongsToWarehouseException(user.getWarehouse().getId());}
        );

        // Check if the section exists
        Section section = sectionRepository.findById(inboundOrder.getSection().getSectionCode()).orElseThrow(() ->{throw new EntityDoesNotExistException(Section.class, inboundOrder.getSection().getSectionCode());});

        inboundOrder.getBatchStock().forEach((bs)->{
            // Check if each section matches with the product associated
            if(
                    productRepository.findById(bs.getProductId()).orElseThrow(
                            () -> { throw new EntityDoesNotExistException(Section.class, bs.getProductId());}
                    ).getProductType()
                            != section.getSectionType())
                throw new SectionAndProductTypeDoesNotMatch(section.getSectionCode(), bs.getProductId());
        });

        // Calculate total capacity needed and currently occupied
        Integer totalCapacityNeeded = inboundOrder.getBatchStock().stream().mapToInt(BatchDTO::getInitialQuantity).sum();
        Integer totalCapacityOccupied = batchRepository.findBySectionSectionCodeEquals(inboundOrder.getSection().getSectionCode()).stream().mapToInt(Batch::getCurrentQuantity).sum();

        // Get the difference between current capacity and needed
        if(section.getCapacity() - totalCapacityOccupied < totalCapacityNeeded)
            throw new NotEnoughSectionCapacityException(section.getSectionCode());

        return section;
    }

    private InboundOrder fromDtoToEntity(InboundOrderRequestDTO dto, Section section){
        // Use of design pattern builder for better understanding
        return InboundOrder.builder()
                .orderNumber(dto.getOrderNumber())
                .orderDate(dto.getOrderDate())
                .section(section)
                .batches(
                        dto.getBatchStock().stream().map(this::fromDtoToEntity).toList()
                ).build();
    }

    private Batch fromDtoToEntity(BatchDTO dto){
        // Use of design pattern builder for better understanding
            return Batch.builder()
                    .batchNumber(dto.getBatchNumber())
                    .currentQuantity(dto.getCurrentQuantity())
                    .dueDate(dto.getDueDate())
                    .manufacturingDate(dto.getManufacturingDate())
                    .initialQuantity(dto.getInitialQuantity())
                    .manufacturingTime(dto.getManufacturingTime())
                    .product(Product.builder().idProduct(dto.getProductId()).build())
                    .build();
    }

    private BatchDTO fromEntityToDto(Batch entity){
        // Use of design pattern builder for better understanding
        return BatchDTO.builder()
                .batchNumber(entity.getBatchNumber())
                .currentTemperature(entity.getProduct().getTemperature())
                .currentQuantity(entity.getCurrentQuantity())
                .initialQuantity(entity.getInitialQuantity())
                .productId(entity.getProduct().getIdProduct())
                .dueDate(entity.getDueDate())
                .manufacturingDate(entity.getManufacturingDate())
                .manufacturingTime(entity.getManufacturingTime())
                .build();
    }
}
