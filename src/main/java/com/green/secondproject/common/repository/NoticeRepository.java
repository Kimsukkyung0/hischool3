package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.NoticeEntity;
import com.green.secondproject.common.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {
    List<NoticeEntity> findByschoolEntity(SchoolEntity entity);
    List<NoticeEntity> findByImptYnAndSchoolEntityOrderByNoticeIdDesc(int imptYn, SchoolEntity schoolEntity);
    List<NoticeEntity> findTop8ByImptYnAndSchoolEntityOrderByNoticeIdDesc(int imptYn, SchoolEntity schoolEntity);
    NoticeEntity findByNoticeId(Long noticeId);
}
