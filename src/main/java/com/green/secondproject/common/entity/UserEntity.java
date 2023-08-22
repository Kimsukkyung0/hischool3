package com.green.secondproject.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.secondproject.common.config.etc.EnrollState;
import com.green.secondproject.common.config.jpa.BaseEntity;
import com.green.secondproject.common.config.security.model.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Table(name="user")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicInsert //null 제외
public class UserEntity extends BaseEntity {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "van_id")
    private VanEntity vanEntity;

    @Column(nullable = false, length = 50, unique = true)
    @Size(min = 5, max = 50)
    private String email;

    @JsonIgnore
    private String pw;

    @Column(nullable = false, length = 20)
    @Size(min = 2, max = 20)
    private String nm;

    private String pic;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false, columnDefinition = "char(13)")
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String detailAddr;

    @JsonIgnore
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

    private String aprPic;

    @Column(nullable = false, columnDefinition = "tinyint default 0")
    private int aprYn;

    @JsonIgnore
    @Column(length = 10, columnDefinition = "default 'ENROLL'")
    @Enumerated(EnumType.STRING)
    @NotNull
    private EnrollState enrollState;
}
