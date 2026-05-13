package com.gym.api.controller;

import com.gym.api.entity.FitnessGoal;
import com.gym.api.entity.FitnessRecord;
import com.gym.api.service.FitnessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/fitness")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "http://localhost:3000")
public class FitnessController {

    private final FitnessService fitnessService;

    // goals
    @PostMapping("/goals/{userId}")
    public ResponseEntity<FitnessGoal> createGoal(@PathVariable Long userId,
                                                  @Valid @RequestBody FitnessGoal goal) {
        return ResponseEntity.ok(fitnessService.createGoal(userId, goal));
    }

    @GetMapping("/goals/{userId}")
    public ResponseEntity<List<FitnessGoal>> getGoals(@PathVariable Long userId) {
        return ResponseEntity.ok(fitnessService.getGoalsByUser(userId));
    }

    @DeleteMapping("/goals/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        fitnessService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }

    // records
    @PostMapping("/records/{userId}")
    public ResponseEntity<FitnessRecord> addRecord(@PathVariable Long userId,
                                                   @Valid @RequestBody FitnessRecord record) {
        return ResponseEntity.ok(fitnessService.addRecord(userId, record));
    }

    @GetMapping("/records/{userId}")
    public ResponseEntity<List<FitnessRecord>> getRecords(@PathVariable Long userId) {
        return ResponseEntity.ok(fitnessService.getRecordsByUser(userId));
    }

    @DeleteMapping("/records/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        fitnessService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
}