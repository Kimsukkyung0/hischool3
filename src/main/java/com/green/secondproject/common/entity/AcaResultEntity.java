package com.green.secondproject.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.secondproject.common.config.jpa.BaseEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="aca_result", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id","year","subject_id", "semester","mid_final"})})
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true) // 부모클래스에 있는 toString 어노테이션을 호출 (만약 부모클래스에도 없으면 해시코드 출력)
@DynamicInsert
public class AcaResultEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long resultId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    @JsonIgnore
    private SubjectEntity subjectEntity;

    @Column(columnDefinition = "char(4)", nullable = false)
    private String year;

    @Column(columnDefinition = "tinyint unsigned not null check(semester in (1, 2))")
    private int semester;

    @Column(name = "mid_final", columnDefinition = "tinyint unsigned not null check(mid_final in (1, 2))")
    private int midFinal;

    @Column(nullable = false, columnDefinition = "tinyint unsigned")
    private int score;

    @Column(nullable = false, columnDefinition = "tinyint unsigned")
    @ColumnDefault(value = "0")
    private int rating;

    @Column(nullable = false, columnDefinition = "tinyint unsigned")
    @ColumnDefault(value = "0")
    private int classRank;

    @Column(nullable = false, columnDefinition = "int unsigned")
    @ColumnDefault(value = "0")
    private int wholeRank;



}
