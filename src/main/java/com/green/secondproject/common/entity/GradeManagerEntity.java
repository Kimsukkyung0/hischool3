package com.green.secondproject.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grade_manager")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeManagerEntity {
    @Id //pk
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "bigint unsigned")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private SchoolEntity schoolEntity;

    @Column(nullable = false, columnDefinition = "char(1)")
    private String grade;

    @Column(nullable = false, columnDefinition = "char(4)")
    private String extNum;
}
