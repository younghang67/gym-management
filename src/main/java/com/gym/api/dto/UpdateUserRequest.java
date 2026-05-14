package com.gym.api.dto;

import com.gym.api.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @Size(min = 2, max = 100)
    private String name;

    @Email(message = "Invalid Email Format")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private Role role;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private Boolean isActive;
}