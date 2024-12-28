package com.MitoDev.FrostVault.repository;

import com.MitoDev.FrostVault.model.dto.BatchNearExpirationDTO;
import com.MitoDev.FrostVault.model.dto.ProductCountByWarehouseDTO;
import com.MitoDev.FrostVault.model.entity.Batch;
import com.MitoDev.FrostVault.model.entity.enums.Type;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;


public interface IBatchRepository extends JpaRepository<Batch, Integer> {

    List<Batch> findByProductIdProductEquals(Integer id);

    List<Batch> findBySectionSectionCodeEquals(Integer sectionCode);

    List<Batch> findByProductIdProductEqualsAndDueDateGreaterThanOrderByDueDateAsc(Integer id, LocalDate dueDate);

    @Query("SELECT new com.MitoDev.FrostVault.model.dto.ProductCountByWarehouseDTO(b.section.warehouse.id, SUM(b.currentQuantity))" +
            " FROM Batch b WHERE b.product.id = :productId GROUP BY b.section.warehouse")
    List<ProductCountByWarehouseDTO> getProductCountByWarehouse(Integer productId);

    @Query("SELECT new com.MitoDev.FrostVault.model.dto.BatchNearExpirationDTO(" +
            "b.batchNumber, b.product.id, b.product.productType, b.dueDate)" +
            "FROM Batch b WHERE b.dueDate <= :dueDate AND b.product.productType = :category")
    List<BatchNearExpirationDTO> getBatchesNearExpirationByCategoryAndOrder(LocalDate dueDate, Type category, Sort order);

    @Query("SELECT new com.MitoDev.FrostVault.model.dto.BatchNearExpirationDTO(" +
            "b.batchNumber, b.product.id, b.product.productType, b.dueDate)" +
            "FROM Batch b WHERE b.dueDate <= :dueDate")
    List<BatchNearExpirationDTO> getBatchesNearExpirationByOrder(LocalDate dueDate, Sort order);
}
