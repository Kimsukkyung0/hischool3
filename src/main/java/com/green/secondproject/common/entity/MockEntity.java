package com.green.secondproject.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name="mock_result", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id","year","subject_id", "mon"})})
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true) // 부모클래스에 있는 toString 어노테이션을 호출 (만약 부모클래스에도 없으면 해시코드 출력)
@DynamicInsert
public class MockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="result_id",updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long resultId;

    @Column(name="user_id", length = 20)
    @NotNull
    private Long userId;

    @Column(name="subject_id", length = 20)
    @NotNull
    private Long subjectId;

    @Column(length = 4)
    @NotNull
    private char year;

    @Column(length = 4)
    @NotNull
    private int mon;

    @Column(name="standard_score", length = 4)
    @NotNull
    private Long standardScore;

    @Column(length = 4)
    @NotNull
    private int rating;

    @Column(length = 4)
    @NotNull
    private int percent;
}
