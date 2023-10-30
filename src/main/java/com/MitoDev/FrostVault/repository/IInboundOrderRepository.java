package com.MitoDev.FrostVault.repository;

import com.MitoDev.FrostVault.model.entity.Batch;
import com.MitoDev.FrostVault.model.entity.InboundOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInboundOrderRepository extends JpaRepository<InboundOrder, Integer> {
}
