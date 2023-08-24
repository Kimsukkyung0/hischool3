//package com.green.secondproject.common.entity;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//import lombok.experimental.SuperBuilder;
//import org.hibernate.annotations.DynamicInsert;
//
//@Entity
//@Table(name="attendance")
//@Data
//@SuperBuilder
//@NoArgsConstructor
//@ToString(callSuper = true)
//@DynamicInsert
//public class AttendanceEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="attend_id", updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
//    private Long attendId;
//
//    @Column(name = "user_id", nullable = false, length = 20)
//    @NotNull
//    private Long userId;
//
//    @Column(nullable = false, length = 4)
//    @NotNull
//    private char grade;
//
//    @Column(name = "lesson_num", nullable = false, length = 20)
//    @NotNull
//    private int lessonNum;
//
//    @Column(name = "disease_absence", nullable = false, length = 4)
//    @NotNull
//    private int diseaseAbsence;
//
//    @Column(name = "unauth_absence", nullable = false, length = 4)
//    @NotNull
//    private int unauthAbsence;
//
//    @Column(name = "etc_absence", nullable = false, length = 4)
//    @NotNull
//    private int etcAbsence;
//
//    @Column(name = "disease_late", nullable = false, length = 4)
//    @NotNull
//    private int diseaseLate;
//
//
//    @Column(name = "unauth_late", nullable = false, length = 4)
//    @NotNull
//    private int unauthLate;
//
//    @Column(name="etc_late", nullable = false, length = 4)
//    @NotNull
//    private int etcLate;
//
//    @Column(name="disease_early", nullable = false, length = 4)
//    @NotNull
//    private int diseaseEarly;
//
//    @Column(name="unhealth_early", nullable = false, length = 4)
//    @NotNull
//    private int unhealthEarly;
//
//    @Column(name="unauth_early", nullable = false, length = 4)
//    @NotNull
//    private int unauthEarly;
//
//    @Column(name="etc_early", nullable = false, length = 4)
//    @NotNull
//    private int etcEarly;
//
//    @Column(name="disease_out", nullable = false, length = 4)
//    @NotNull
//    private int diseaseOut;
//
//    @Column(name="unauth_out", nullable = false, length = 4)
//    @NotNull
//    private int authOut;
//
//    @Column(name="special_note", length = 20)
//    private String specialNote;
//}
