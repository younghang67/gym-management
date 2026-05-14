package com.gym.api.dto;

import com.gym.api.entity.Status;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignMembershipRequest {
    @NotNull(message = "User ID is required")
    private Long userId;
    @NotNull(message = "Membership ID is required")
    private Long membershipId;
    private LocalDate startDate;
    private Status status;

}
