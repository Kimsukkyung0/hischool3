package com.green.secondproject.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="awards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AwardsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long awardId;

    @JoinColumn(name = "user_id",nullable = false)
    @ManyToOne
    private UserEntity userEntity;

    @Column(nullable = false, columnDefinition = "varchar(100)")
    private String nm;

    @Column(nullable = false, columnDefinition = "varchar(20)")
    @NotNull
    private String prize;

    @Column(nullable = false)
    @NotNull
    private LocalDate awardedAt;

    @Column(nullable = false, columnDefinition = "varchar(20)")
    @NotNull
    private String awardedBy;

    @Column(nullable = false, columnDefinition = "varchar(20)")
    @NotNull
    private String participation;

}
