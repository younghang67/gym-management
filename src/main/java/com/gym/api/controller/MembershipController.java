package com.gym.api.controller;

import com.gym.api.dto.AssignMembershipRequest;
import com.gym.api.dto.MembershipRequest;
import com.gym.api.entity.Membership;
import com.gym.api.entity.Status;
import com.gym.api.service.MembershipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/memberships")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "http://localhost:3000")
public class MembershipController {

    private final MembershipService membershipService;

    @PostMapping
    public ResponseEntity<Membership> createMembership(@Valid @RequestBody MembershipRequest request) {
        return ResponseEntity.ok(membershipService.createMembership(request));
    }

    @PostMapping("/assign")
    public ResponseEntity<Void> assignMembership(
            @Valid @RequestBody AssignMembershipRequest request) {
        membershipService.assignMembership(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Membership>> getAllMemberships() {
        return ResponseEntity.ok(membershipService.getAllMemberships());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Membership>> getMembershipsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(membershipService.getMembershipsByUser(userId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,
                                             @RequestParam Status status) {
        membershipService.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembership(@PathVariable Long id) {
        membershipService.deleteMembership(id);
        return ResponseEntity.noContent().build();
    }
}