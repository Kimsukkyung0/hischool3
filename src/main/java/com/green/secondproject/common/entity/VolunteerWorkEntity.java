package com.green.secondproject.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="volunteer_rec")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class VolunteerWorkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long volunteerId;

    @JoinColumn(name = "user_id",nullable = false)
    @ManyToOne
    private UserEntity userEntity;

    @Column(nullable = false, columnDefinition = "char(1)")
    private String grade;

    @Column(nullable = false)
    @NotNull
    private LocalDate startDate;

    @Column(nullable = false)
    @NotNull
    private LocalDate endDate;

    @Column(nullable = false)
    @NotNull
    private String place;

    @Column(nullable = false)
    @NotNull
    private String ctnt;

    @Column(nullable = false,columnDefinition = "tinyint")
    @NotNull
    private int hrs;

    @Column(nullable = false)
    @NotNull
    private int totalHrs;
}
