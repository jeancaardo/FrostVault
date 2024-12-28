package com.MitoDev.FrostVault.controller;

import com.MitoDev.FrostVault.model.dto.*;
import com.MitoDev.FrostVault.service.interfaces.IPurchaseOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fresh-products/orders")
public class PurchaseOrderController {

    private final IPurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(IPurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping()
    public ResponseEntity<ProductsReponseDTO> getAllProductsInOrder() {
        return ResponseEntity.ok().body(
                        purchaseOrderService.getAllProductsInPurchaseOrder()
        );
    }

    @GetMapping("/execute")
    public ResponseEntity<PurchaseOrderExecutedDTO> confirmPurchaseOrder() {
        return ResponseEntity.ok().body(
                purchaseOrderService.executePurchaseOrder()
        );
    }
    @PostMapping
    public ResponseEntity<PurchaseOrderTotalPriceResponseDTO> createNewPurchaseOrder()  {
        return ResponseEntity.status(201).body(
                purchaseOrderService.initializePurchaseOrder()
        );
    }

    @DeleteMapping
    public ResponseEntity<PurchaseOrderTotalPriceResponseDTO> deletePoductFromPurchaseOrder(@RequestBody ProductDTO dto)  {
        return ResponseEntity.status(200).body(
                purchaseOrderService.deleteProductFromPurchaseOrder(dto)
        );
    }

    @PutMapping
    public ResponseEntity<PurchaseOrderTotalPriceResponseDTO> addPoductToPurchaseOrder(@RequestBody ProductDTO dto) {
        return ResponseEntity.status(201).body(
                purchaseOrderService.addProductToPurchaseOrder(dto)
        );
    }
}
