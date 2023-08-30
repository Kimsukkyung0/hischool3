package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.NoticeEntity;
import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.entity.VanEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {
    List<NoticeEntity> findByschoolEntity(SchoolEntity entity);
    List<NoticeEntity> findByImptYnAndSchoolEntityOrderByNoticeIdDesc(int imptYn, SchoolEntity schoolEntity);
    List<NoticeEntity> findTop8ByImptYnAndSchoolEntityOrderByNoticeIdDesc(int imptYn, SchoolEntity schoolEntity);


    @Query("SELECT n FROM NoticeEntity n " +
            "JOIN n.schoolEntity s " +
            "JOIN VanEntity v ON n.schoolEntity = v.schoolEntity " +
            "JOIN UserEntity u ON u.vanEntity = v " +
            "WHERE n.imptYn = 1 AND v.vanId = :vanId")
    List<NoticeEntity> findImportantNoticesByVanId(@Param("vanId") Long vanId);

    @Query("SELECT n FROM NoticeEntity n " +
            "JOIN n.schoolEntity s " +
            "JOIN VanEntity v ON n.schoolEntity = v.schoolEntity " +
            "JOIN UserEntity u ON u.vanEntity = v " +
            "WHERE n.imptYn = 0 AND v.vanId = :vanId")
    List<NoticeEntity> findTopByImptYnAndSchoolEntityOrderByNoticeIdDesc(@Param("vanId") Long vanId);


    NoticeEntity findByNoticeId(Long noticeId);
}
