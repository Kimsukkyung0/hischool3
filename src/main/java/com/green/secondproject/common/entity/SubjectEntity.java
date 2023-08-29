package com.green.secondproject.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subject", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"category_id","nm"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectEntity {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long subjectId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private SbjCategoryEntity sbjCategoryEntity;

    @Column(nullable = false, length = 20)
    private String nm;
}
