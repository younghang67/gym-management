package com.gym.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MembershipRequest {

    @NotBlank(message = "Membership name is required")
    private String name;

    @NotBlank(message = "Price is required")
    private BigDecimal price;

    @NotBlank(message = "Duration days is required")
    private Integer durationDays;

    private String description;

}
