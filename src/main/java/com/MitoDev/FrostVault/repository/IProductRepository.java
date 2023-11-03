package com.MitoDev.FrostVault.repository;

import com.MitoDev.FrostVault.model.entity.InboundOrder;
import com.MitoDev.FrostVault.model.entity.Product;
import com.MitoDev.FrostVault.model.entity.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByProductTypeEquals(Type productType);


}
