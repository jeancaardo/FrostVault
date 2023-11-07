package com.MitoDev.FrostVault.controller;

import com.MitoDev.FrostVault.model.dto.BatchStockResponseDTO;
import com.MitoDev.FrostVault.service.interfaces.IBatchService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class BatchController {

    private final IBatchService batchService;

    public BatchController(IBatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping("/{idProduct}/batch/list")
    public BatchStockResponseDTO getAllBatchesByProductId(@PathVariable Integer idProduct, @RequestParam(value = "order", required = false) Character order){
        return order == '\u0000' ?
                 batchService.getBatchesByProduct(idProduct)
                :
                batchService.getBatchesByProductOrdered(idProduct, order);
    }
}
