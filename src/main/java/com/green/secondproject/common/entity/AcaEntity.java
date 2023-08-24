package com.green.secondproject.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name="aca_result")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true) // 부모클래스에 있는 toString 어노테이션을 호출 (만약 부모클래스에도 없으면 해시코드 출력)
@DynamicInsert
public class AcaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="result_id", updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long resultId;

    @ManyToOne
    @JoinColumn(
             referencedColumnName = "userId"
            , nullable = false)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(referencedColumnName = "subjectId"
            , nullable = false)
    private SubjectEntity subjectEntity;

    @Column(nullable = false, length = 4)
    @NotNull
    private char year;

    @Column(nullable = false, length = 4)
    @NotNull
    private int semester;

    @Column(name = "mid_final", nullable = false, length = 4)
    @NotNull
    private int midFinal;

    @Column(nullable = false, length = 4)
    @NotNull
    private int score;

    @Column(name = "class_rank", nullable = false, length = 4)
    @NotNull
    private int classRank;

    @Column(name = "whole_rank", nullable = false, length = 11)
    @NotNull
    private Long wholeRank;
}
