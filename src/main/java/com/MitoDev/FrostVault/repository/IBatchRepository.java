package com.MitoDev.FrostVault.repository;

import com.MitoDev.FrostVault.model.entity.Batch;
import com.MitoDev.FrostVault.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IBatchRepository extends JpaRepository<Batch, Integer> {

    List<Batch> findBySectionSectionCodeEquals(Integer sectionCode);

    List<Batch> findByProductIdEqualsAndDueDateGreaterThanOrderByDueDateAscending(Integer id, LocalDate dueDate);
}
