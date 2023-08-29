package com.green.secondproject.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sbj_category", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nm","type"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SbjCategoryEntity {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long categoryId;

    @Column(nullable = false, length = 10)
    private String nm;

    @Column(nullable = false, columnDefinition = "tinyint")
    private int type;

}
