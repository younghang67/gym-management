package com.gym.api.repository;

import com.gym.api.entity.Role;
import com.gym.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);

    Long countByRole(Role role);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);
}