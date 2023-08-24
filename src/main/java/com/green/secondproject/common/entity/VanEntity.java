package com.green.secondproject.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="van", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"school_id","year","grade", "class_num"})})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VanEntity {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long vanId;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private SchoolEntity schoolEntity;

    @Column(nullable = false, columnDefinition = "char(4)")
    private String year;

    @Column(nullable = false, columnDefinition = "char(1)")
    private String grade;

    @Column(name = "class_num", nullable = false, columnDefinition = "char(2)")
    private String classNum;
}
