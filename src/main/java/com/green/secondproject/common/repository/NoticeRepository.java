package com.green.secondproject.common.repository;
import com.green.secondproject.common.entity.NoticeEntity;
import com.green.secondproject.common.entity.SchoolEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {
    List<NoticeEntity> findByschoolEntity(SchoolEntity entity,Pageable pageable);
    List<NoticeEntity> findByImptYnAndSchoolEntityOrderByNoticeIdDesc(int imptYn, SchoolEntity schoolEntity);
    List<NoticeEntity> findTop17ByImptYnAndSchoolEntityOrderByNoticeIdDesc(int imptYn, SchoolEntity schoolEntity);
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
    List<NoticeEntity> findByImptYn(Long imptYn);
    Page<NoticeEntity> findByTitleContainingAndImptYnNot(String search, int i, Pageable pageable);
    Page<NoticeEntity> findByImptYnNot(int i, Pageable pageable);
    List<NoticeEntity> findByImptYnAndSchoolEntitySchoolId(Long imptYn, Long schoolId);
    Page<NoticeEntity> findByImptYnNotAndSchoolEntitySchoolId(Integer imptYn, Long schoolId, Pageable pageable);
    long countBySchoolEntitySchoolId(Long schoolId);
    long countByTitleContainingAndImptYnNotAndSchoolEntitySchoolId(String search,Long imptYn,Long schoolId);
    long countByImptYnAndSchoolEntitySchoolId(Long imptYn,Long schoolId);
    @Query("SELECT count(*) FROM NoticeEntity WHERE"+
            "(:search IS NULL OR title LIKE CONCAT('%', :search, '%'))")
    long countBy(@Param("search") String search);


}





