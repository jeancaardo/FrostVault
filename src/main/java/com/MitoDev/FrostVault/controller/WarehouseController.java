package com.MitoDev.FrostVault.controller;

import com.MitoDev.FrostVault.model.dto.ProductCountsDTO;
import com.MitoDev.FrostVault.service.interfaces.IWarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class WarehouseController {

    private final IWarehouseService warehouseService;

    public WarehouseController(IWarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("/{idProduct}/warehouse/list")
    public ResponseEntity<ProductCountsDTO> getCountOfProductsByWarehouse(@PathVariable Integer idProduct){
        return ResponseEntity.ok(warehouseService.getProductCountAtEachWarehouse(idProduct));
    }
}
