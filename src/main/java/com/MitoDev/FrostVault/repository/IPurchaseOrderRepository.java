package com.MitoDev.FrostVault.repository;

import com.MitoDev.FrostVault.model.entity.Batch;
import com.MitoDev.FrostVault.model.entity.PurchaseOrder;
import com.MitoDev.FrostVault.model.entity.enums.PurchaseOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IPurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

    Optional<PurchaseOrder> findByUserIdAndStatusCodeEquals(Integer userId, PurchaseOrderStatus statusCode);
}
