package com.MitoDev.FrostVault.repository;

import com.MitoDev.FrostVault.model.dto.ProductCountByWarehouseDTO;
import com.MitoDev.FrostVault.model.entity.Batch;
import com.MitoDev.FrostVault.model.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IWarehouseRepository extends JpaRepository<Warehouse, Integer> {
}
