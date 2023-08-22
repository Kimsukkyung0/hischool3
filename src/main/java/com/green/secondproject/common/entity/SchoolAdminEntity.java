package com.green.secondproject.common.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="school_admin")
@Data
public class SchoolAdminEntity {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long adminId;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String pw;
}
