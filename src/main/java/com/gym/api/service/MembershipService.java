package com.gym.api.service;

import com.gym.api.dto.AssignMembershipRequest;
import com.gym.api.dto.MembershipRequest;
import com.gym.api.entity.Membership;
import com.gym.api.entity.Status;
import com.gym.api.entity.User;
import com.gym.api.entity.UserMembership;
import com.gym.api.repository.MembershipRepository;
import com.gym.api.repository.UserMembershipRepository;
import com.gym.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final UserMembershipRepository userMembershipRepository;
    private final UserRepository userRepository;

    // membership plan

    public Membership createMembership(MembershipRequest request) {
        Membership membership = Membership.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .durationDays(request.getDurationDays())  // ← fixed
                .build();
        return membershipRepository.save(membership);
    }

    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

    @Transactional
    public void deleteMembership(Long id) {
        if (!membershipRepository.existsById(id)) {
            throw new RuntimeException("Membership not found");
        }
        membershipRepository.deleteById(id);
    }


    public void assignMembership(AssignMembershipRequest request) {
        if (request.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        if (request.getMembershipId() == null) {
            throw new IllegalArgumentException("Membership ID is required");
        }
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        Membership membership = membershipRepository.findById(request.getMembershipId())
                .orElseThrow(() -> new RuntimeException("Membership not found: " + request.getMembershipId()));

        LocalDate startDate = request.getStartDate() != null
                ? request.getStartDate()
                : LocalDate.now();

        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }
        LocalDate endDate = startDate.plusDays(membership.getDurationDays());

        boolean hasActiveMembership = userMembershipRepository
                .existsByUserIdAndStatusAndEndDateAfter(
                        request.getUserId(),
                        Status.ACTIVE,
                        LocalDate.now()
                );

        if (hasActiveMembership) {
            throw new IllegalStateException(
                    "User already has an active membership"
            );
        }

        Status status = request.getStatus() != null
                ? request.getStatus()
                : Status.ACTIVE;

        UserMembership userMembership = UserMembership.builder()
                .user(user)
                .membership(membership)
                .startDate(startDate)
                .endDate(endDate)
                .status(status)
                .build();

        userMembershipRepository.save(userMembership);
    }

    public List<Membership> getMembershipsByUser(Long userId) {
        return userMembershipRepository.findByUserId(userId)
                .stream()
                .map(UserMembership::getMembership)
                .toList();
    }

    public void updateStatus(Long id, Status status) {
        UserMembership userMembership = userMembershipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User membership not found"));
        userMembership.setStatus(status);
        userMembershipRepository.save(userMembership);
    }

    public Long countByStatus(String status) {
        return userMembershipRepository.countByStatus(Status.valueOf(status));
    }
}