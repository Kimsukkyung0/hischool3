package com.green.secondproject.common.entity;

import com.green.secondproject.common.config.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="mock_result", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id","year","subject_id", "mon"})})
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true) // 부모클래스에 있는 toString 어노테이션을 호출 (만약 부모클래스에도 없으면 해시코드 출력)
@DynamicInsert
public class MockResultEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="result_id",updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long resultId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectEntity subjectEntity;

    @Column(nullable = false, columnDefinition = "char(4)")
    private String year;

    @Column(nullable = false, columnDefinition = "char(2)")
    private String mon;

    @Column(columnDefinition = "tinyint unsigned", nullable = false)
    private int standardScore;

    @Column(columnDefinition = "tinyint unsigned", nullable = false)
    private int rating;

    @Column(columnDefinition = "tinyint unsigned", nullable = false)
    private int percent;
}
