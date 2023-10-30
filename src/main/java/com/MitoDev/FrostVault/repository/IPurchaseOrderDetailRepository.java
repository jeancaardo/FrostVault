package com.MitoDev.FrostVault.repository;

import com.MitoDev.FrostVault.model.entity.Batch;
import com.MitoDev.FrostVault.model.entity.PurchaseOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchaseOrderDetailRepository extends JpaRepository<PurchaseOrderDetail, Integer> {
}
