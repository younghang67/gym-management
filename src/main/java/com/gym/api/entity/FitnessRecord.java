package com.gym.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "fitness_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FitnessRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private BigDecimal weight;
    private BigDecimal height;
    private BigDecimal bmi;

    @Column(name = "chest_size")
    private BigDecimal chestSize;

    @Column(name = "waist_size")
    private BigDecimal waistSize;

    @Column(name = "biceps_size")
    private BigDecimal bicepsSize;

    @Column(name = "body_fat_percentage")
    private BigDecimal bodyFatPercentage;

    @CreationTimestamp
    @Column(name = "recorded_at", updatable = false)
    private Timestamp recordedAt;
}
