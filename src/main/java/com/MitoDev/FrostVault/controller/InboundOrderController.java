package com.MitoDev.FrostVault.controller;

import com.MitoDev.FrostVault.model.dto.BatchStockDTO;
import com.MitoDev.FrostVault.model.dto.InboundOrderRequestDTO;
import com.MitoDev.FrostVault.repository.IInboundOrderRepository;
import com.MitoDev.FrostVault.service.interfaces.IInboundOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fresh-products/inboundorder")
public class InboundOrderController {

    private IInboundOrderService inboundOrderService;

    public InboundOrderController(IInboundOrderService inboundOrderService) {
        this.inboundOrderService = inboundOrderService;
    }

    @PostMapping("/")
    public ResponseEntity<BatchStockDTO> createInboundOrder(@RequestBody @Valid InboundOrderRequestDTO req) throws JsonProcessingException {
        return ResponseEntity.status(201).body(
                inboundOrderService.addNewInboundOrder(req)
        );
    }

    @PutMapping("/")
    public ResponseEntity<BatchStockDTO> updateInboundOrder(@RequestBody @Valid InboundOrderRequestDTO req) throws JsonProcessingException {
        return ResponseEntity.status(201).body(
                inboundOrderService.updateNewInboundOrder(req)
        );
    }
}
