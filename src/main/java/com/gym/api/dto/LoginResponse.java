package com.gym.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private UserInfo user;

    @Data
    @AllArgsConstructor
    public static class UserInfo {
        private Long id;
        private String name;
        private String role;
        private String phoneNumber;
        private Boolean isActive;
        private String email;
    }
}