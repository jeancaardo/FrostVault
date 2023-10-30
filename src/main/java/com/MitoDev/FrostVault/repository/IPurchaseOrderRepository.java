package com.MitoDev.FrostVault.repository;

import com.MitoDev.FrostVault.model.entity.Batch;
import com.MitoDev.FrostVault.model.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
}
