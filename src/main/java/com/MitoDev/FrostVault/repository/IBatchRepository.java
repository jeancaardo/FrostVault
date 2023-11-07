package com.MitoDev.FrostVault.repository;

import com.MitoDev.FrostVault.model.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;


public interface IBatchRepository extends JpaRepository<Batch, Integer> {

    List<Batch> findByProductIdProductEquals(Integer id);

    List<Batch> findBySectionSectionCodeEquals(Integer sectionCode);

    List<Batch> findByProductIdProductEqualsAndDueDateGreaterThanOrderByDueDateAsc(Integer id, LocalDate dueDate);
}
