package com.gym.api.repository;

import com.gym.api.entity.Status;
import com.gym.api.entity.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserMembershipRepository extends JpaRepository<UserMembership, Long> {

    // Your existing methods
    boolean existsByUserIdAndStatusAndEndDateAfter(Long userId, Status status, LocalDate date);

    List<UserMembership> findByUserId(Long userId);

    Long countByStatus(Status status);

    // Additional helpful methods

    // Find active membership for a user (not expired)
    Optional<UserMembership> findByUserIdAndStatusAndEndDateAfter(Long userId, Status status, LocalDate date);

    // Find all active memberships
    List<UserMembership> findAllByStatusAndEndDateAfter(Status status, LocalDate date);

    // Find expired memberships
    List<UserMembership> findAllByStatusAndEndDateBefore(Status status, LocalDate date);

    // Check if user has any membership (active or expired)
    boolean existsByUserId(Long userId);

    // Get user's current active membership
    @Query("SELECT um FROM UserMembership um WHERE um.user.id = :userId " +
            "AND um.status = :status AND um.endDate > :currentDate")
    Optional<UserMembership> findCurrentActiveMembership(@Param("userId") Long userId,
                                                         @Param("status") Status status,
                                                         @Param("currentDate") LocalDate currentDate);

    // Deactivate expired memberships (for batch jobs)
    @Modifying
    @Transactional
    @Query("UPDATE UserMembership um SET um.status = :expiredStatus " +
            "WHERE um.status = :activeStatus AND um.endDate < :currentDate")
    int deactivateExpiredMemberships(@Param("expiredStatus") Status expiredStatus,
                                     @Param("activeStatus") Status activeStatus,
                                     @Param("currentDate") LocalDate currentDate);
}