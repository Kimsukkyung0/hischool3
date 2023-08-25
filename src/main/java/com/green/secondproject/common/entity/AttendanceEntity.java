package com.green.secondproject.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name="attendance")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@DynamicInsert
public class AttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long attendId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private UserEntity userEntity;

    @Column(columnDefinition = "char(1)", nullable = false)
    private String grade;

    @ColumnDefault(value = "0")
    @Column(nullable = false)
    private Integer lessonNum;

    @Column(columnDefinition = "tinyint", nullable = false)
    @ColumnDefault(value = "0")
    private Integer diseaseAbsence;

    @Column(columnDefinition = "tinyint", nullable = false)
    @ColumnDefault(value = "0")
    private Integer unauthAbsence;

    @Column(columnDefinition = "tinyint", nullable = false)
    @ColumnDefault(value = "0")
    private Integer etcAbsence;

    @Column(columnDefinition = "tinyint", nullable = false)
    @ColumnDefault(value = "0")
    private Integer diseaseLate;

    @Column(columnDefinition = "tinyint", nullable = false)
    @ColumnDefault(value = "0")
    private Integer unauthLate;

    @Column(columnDefinition = "tinyint", nullable = false)
    @ColumnDefault(value = "0")
    private Integer etcLate;

    @Column(columnDefinition = "tinyint", nullable = false)
    @ColumnDefault(value = "0")
    private Integer diseaseEarly;

    @Column(columnDefinition = "tinyint", nullable = false)
    @ColumnDefault(value = "0")
    private Integer unauthEarly;

    @Column(columnDefinition = "tinyint", nullable = false)
    @ColumnDefault(value = "0")
    private Integer etcEarly;

    @Column(columnDefinition = "tinyint", nullable = false)
    @ColumnDefault(value = "0")
    private Integer diseaseOut;

    @Column(columnDefinition = "tinyint", nullable = false)
    @ColumnDefault(value = "0")
    private Integer unauthOut;

    @Column(columnDefinition = "tinyint", nullable = false)
    @ColumnDefault(value = "0")
    private Integer etcOut;

    @Column(length = 20)
    private String specialNote;
}
