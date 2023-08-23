package com.green.secondproject.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="school")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolEntity {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long schoolId;

    @Column(nullable = false, length = 50, unique = true)
    private String nm;

    @Column(nullable = false, columnDefinition = "char(7)", unique = true)
    private String code;

    @Column(length = 100)
    private String logo;
}
