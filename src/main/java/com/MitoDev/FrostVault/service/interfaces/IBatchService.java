package com.MitoDev.FrostVault.service.interfaces;

import com.MitoDev.FrostVault.model.dto.BatchStockNearExpirationDTO;
import com.MitoDev.FrostVault.model.dto.BatchStockResponseDTO;

public interface IBatchService {

    BatchStockResponseDTO getBatchesByProduct(Integer productId);

    BatchStockResponseDTO getBatchesByProductOrdered(Integer productId, Character order);

    BatchStockNearExpirationDTO getAllBatchesCloseToExpire(Integer days, String category, String order);

}
