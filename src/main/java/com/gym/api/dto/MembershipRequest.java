package com.gym.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MembershipRequest {

    private Long membershipId;   // used when assigning membership to user

    @NotBlank(message = "Membership name is required")
    private String name;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotNull(message = "Duration days is required")
    private Integer durationDays;

    private String description;
}