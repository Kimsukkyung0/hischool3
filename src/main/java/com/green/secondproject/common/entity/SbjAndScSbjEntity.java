package com.green.secondproject.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SbjAndScSbjEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(columnDefinition = "BINGINT UNSIGNED",updatable = false)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "subject_id")
        private SubjectEntity subjectEntity;

        @ManyToOne
        @JoinColumn(name = "category_id")
        private SbjCategoryEntity sbjCategoryEntity;
}
