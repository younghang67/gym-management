package com.gym.api.controller;

import com.gym.api.service.MembershipService;
import com.gym.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminDashboardController {

    private final UserService userService;
    private final MembershipService membershipService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getDashboard() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userService.countByRole("USER"));
        stats.put("totalTrainers", userService.countByRole("TRAINER"));
        stats.put("activeMemberships", membershipService.countByStatus("ACTIVE"));
        stats.put("expiredMemberships", membershipService.countByStatus("EXPIRED"));
        return ResponseEntity.ok(stats);
    }
}