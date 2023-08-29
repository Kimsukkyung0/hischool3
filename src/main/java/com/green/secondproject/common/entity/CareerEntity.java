package com.green.secondproject.common.entity;

import com.green.secondproject.common.config.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "career")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CareerEntity extends BaseEntity {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long career_id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity userEntity;

    @Column(nullable = false,length = 1)
    private String grade;

    @Column(nullable = false,length = 50)
    private String interest;

    @Column(nullable = false,length = 20)
    private String std_hope;

    @Column(nullable = false,length = 20)
    private String parent_hope;

    @Column(nullable = false,length = 50)
    private String hope_univ;

    @Column(nullable = false,length = 20)
    private String hope_dept;

    @Column(nullable = false,length = 200)
    private String special_note;

}
