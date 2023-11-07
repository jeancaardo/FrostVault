package com.MitoDev.FrostVault.service;

import com.MitoDev.FrostVault.exception.custom.NoProductsException;
import com.MitoDev.FrostVault.model.dto.ProductCountsDTO;
import com.MitoDev.FrostVault.repository.IBatchRepository;
import com.MitoDev.FrostVault.service.interfaces.IWarehouseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService implements IWarehouseService {

    private final IBatchRepository batchRepository;

    public WarehouseService(IBatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    @Override
    public ProductCountsDTO getProductCountAtEachWarehouse(Integer idProduct) {

        var list = batchRepository.getProductCountByWarehouse(idProduct);

        if(list.isEmpty())
            throw new NoProductsException();

        return ProductCountsDTO.builder()
                .productCountByWarehouse(list)
                .build();
    }
}
