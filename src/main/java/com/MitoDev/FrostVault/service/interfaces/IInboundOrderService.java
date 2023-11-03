package com.MitoDev.FrostVault.service.interfaces;

import com.MitoDev.FrostVault.model.dto.BatchStockDTO;
import com.MitoDev.FrostVault.model.dto.InboundOrderRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface IInboundOrderService {
    BatchStockDTO addNewInboundOrder(InboundOrderRequestDTO req);

    BatchStockDTO updateNewInboundOrder(InboundOrderRequestDTO req);
}
