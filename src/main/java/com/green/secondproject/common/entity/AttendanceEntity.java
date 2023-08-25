package com.green.secondproject.common.entity;

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
    @NotNull
    private Long attendId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private UserEntity userEntity;

    @Column(columnDefinition = "char(1)", nullable = false)
    @NotNull
    private String grade;

    @NotNull
    @ColumnDefault(value = "0")
    private int lessonNum;

    @Column(columnDefinition = "tinyint not null")
    @ColumnDefault(value = "0")
    @NotNull
    private int diseaseAbsence;

    @Column(columnDefinition = "tinyint not null")
    @ColumnDefault(value = "0")
    @NotNull
    private int unauthAbsence;

    @Column(columnDefinition = "tinyint not null")
    @ColumnDefault(value = "0")
    @NotNull
    private int etcAbsence;

    @Column(columnDefinition = "tinyint not null")
    @ColumnDefault(value = "0")
    @NotNull
    private int diseaseLate;

    @Column(columnDefinition = "tinyint not null")
    @ColumnDefault(value = "0")
    @NotNull
    private int unauthLate;

    @Column(columnDefinition = "tinyint not null")
    @ColumnDefault(value = "0")
    @NotNull
    private int etcLate;

    @Column(columnDefinition = "tinyint not null")
    @ColumnDefault(value = "0")
    @NotNull
    private int diseaseEarly;

    @Column(columnDefinition = "tinyint not null")
    @ColumnDefault(value = "0")
    @NotNull
    private int unauthEarly;

    @Column(columnDefinition = "tinyint not null")
    @ColumnDefault(value = "0")
    @NotNull
    private int etcEarly;

    @Column(columnDefinition = "tinyint not null")
    @ColumnDefault(value = "0")
    @NotNull
    private int diseaseOut;

    @Column(columnDefinition = "tinyint not null")
    @ColumnDefault(value = "0")
    @NotNull
    private int unauthOut;

    @Column(columnDefinition = "tinyint not null")
    @ColumnDefault(value = "0")
    @NotNull
    private int etcOut;

    @Column(length = 20)
    private String specialNote;
}
