package com.MitoDev.FrostVault.repository;

import com.MitoDev.FrostVault.model.entity.Batch;
import com.MitoDev.FrostVault.model.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISectionRepository extends JpaRepository<Section, Integer> {
}
