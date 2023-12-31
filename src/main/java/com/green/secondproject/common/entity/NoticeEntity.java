package com.green.secondproject.common.entity;

import com.green.secondproject.common.config.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "notice")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NoticeEntity extends BaseEntity {
    @Id //pk
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long noticeId;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity schoolEntity;

    @Column(nullable = false,length = 100)
    private String title;

    @Column(nullable = false,columnDefinition = "text")
    private String content;

    @Column(name = "impt_yn",nullable = false,columnDefinition = "tinyint default 0")
    private int imptYn;

    @Column(nullable = false,columnDefinition = "int default 0")
    private int hits;

}
