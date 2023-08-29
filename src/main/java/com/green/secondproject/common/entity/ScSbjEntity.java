package com.green.secondproject.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.secondproject.common.config.etc.Grade;
import com.green.secondproject.common.config.security.model.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "sc_sbj", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"grade","subject_id","school_id"})
})
public class ScSbjEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name="sc_sbj_id",updatable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long schoolSbjId;

    @JoinColumn(name = "school_id")
    @ManyToOne
    @NotNull
    private SchoolEntity schoolEntity;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity entity;

    @JsonIgnore
    @Column(length = 1,nullable = false)
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Grade grade;
}
