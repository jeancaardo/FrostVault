package com.MitoDev.FrostVault.repository;

import com.MitoDev.FrostVault.model.entity.Batch;
import com.MitoDev.FrostVault.model.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWarehouseRepository extends JpaRepository<Warehouse, Integer> {
}
