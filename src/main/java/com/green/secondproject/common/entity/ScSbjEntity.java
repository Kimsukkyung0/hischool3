package com.green.secondproject.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@Table(name = "sc_sbj", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"grade","subject_id","school_id"})
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
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
    private SubjectEntity subjectEntity;

    @JsonIgnore
    @Column(length = 1,nullable = false)
    @NotNull
    private String grade;

}
