package com.green.secondproject.common.config.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@NoArgsConstructor
public class BaseEntity {
    @CreatedDate
    @Column(updatable = false, nullable = false)
    @ColumnDefault(value = "current_timestamp")
    private LocalDateTime createdAt;

    @JsonIgnore
    @LastModifiedDate
    @Column(nullable = false)
    @ColumnDefault(value = "current_timestamp")
    private LocalDateTime updatedAt;
}
