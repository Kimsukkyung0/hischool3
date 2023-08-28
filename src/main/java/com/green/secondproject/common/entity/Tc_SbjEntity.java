//package com.green.secondproject.common.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Data
//@Table(name = "tc_sbj", uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"subject_id","nm"})
//})
//public class Tc_SbjEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
//    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
//    private Long userId;
//
//    @ManyToOne
//    @JoinColumn(name = "subject_id")
//    private SubjectEntity entity;
//}
