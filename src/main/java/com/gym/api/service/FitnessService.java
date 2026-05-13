package com.gym.api.service;

import com.gym.api.entity.FitnessGoal;
import com.gym.api.entity.FitnessRecord;
import com.gym.api.entity.User;
import com.gym.api.repository.FitnessGoalRepository;
import com.gym.api.repository.FitnessRecordRepository;
import com.gym.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FitnessService {

    private final FitnessGoalRepository fitnessGoalRepository;
    private final FitnessRecordRepository fitnessRecordRepository;
    private final UserRepository userRepository;

    // ─── Goals ───────────────────────────────────────────────

    public FitnessGoal createGoal(Long userId, FitnessGoal goal) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        goal.setUser(user);
        return fitnessGoalRepository.save(goal);
    }

    public List<FitnessGoal> getGoalsByUser(Long userId) {
        return fitnessGoalRepository.findByUserId(userId);
    }

    @Transactional
    public void deleteGoal(Long id) {
        if (!fitnessGoalRepository.existsById(id)) {
            throw new RuntimeException("Goal not found");
        }
        fitnessGoalRepository.deleteById(id);
    }

    // ─── Records ─────────────────────────────────────────────

    public FitnessRecord addRecord(Long userId, FitnessRecord record) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        record.setUser(user);
        return fitnessRecordRepository.save(record);
    }

    public List<FitnessRecord> getRecordsByUser(Long userId) {
        return fitnessRecordRepository.findByUserId(userId);
    }

    @Transactional
    public void deleteRecord(Long id) {
        if (!fitnessRecordRepository.existsById(id)) {
            throw new RuntimeException("Record not found");
        }
        fitnessRecordRepository.deleteById(id);
    }
}