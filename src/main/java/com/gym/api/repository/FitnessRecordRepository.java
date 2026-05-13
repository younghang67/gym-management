package com.gym.api.repository;

import com.gym.api.entity.FitnessRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FitnessRecordRepository extends JpaRepository<FitnessRecord, Long> {
    List<FitnessRecord> findByUserId(Long userId);
}