package com.gym.api.service;

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

    // user lai assign garne

    public void assignMembership(Long userId, MembershipRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Membership membership = membershipRepository.findById(request.getMembershipId())
                .orElseThrow(() -> new RuntimeException("Membership not found"));

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(membership.getDurationDays());  // ← fixed

        UserMembership userMembership = UserMembership.builder()
                .user(user)
                .membership(membership)
                .startDate(startDate)
                .endDate(endDate)
                .status(Status.ACTIVE)
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