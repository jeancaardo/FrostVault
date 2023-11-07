package com.MitoDev.FrostVault.service.interfaces;

import com.MitoDev.FrostVault.model.dto.ProductCountsDTO;

import java.util.List;

public interface IWarehouseService {

    ProductCountsDTO getProductCountAtEachWarehouse(Integer idProduct);
}
