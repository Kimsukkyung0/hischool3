package com.green.secondproject.notice;

import com.green.secondproject.admin.model.StudentClassVo;
import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.NoticeEntity;
import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.entity.VanEntity;
import com.green.secondproject.common.repository.NoticeRepository;
import com.green.secondproject.common.repository.SchoolRepository;
import com.green.secondproject.common.repository.UserRepository;
import com.green.secondproject.notice.model.*;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@ToString
@RequiredArgsConstructor
@Slf4j
public class NoticeService {
    private final NoticeMapper mapper;
    private final NoticeRepository noticeRepository;
    private final AuthenticationFacade facade;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;

    public NoticeListVo noticeList(int page) {
        MyUserDetails userDetails = facade.getLoginUser();
        Long userSchoolId = userDetails.getSchoolId();

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, 14, sort);
        List<NoticeTotalVo> result = new ArrayList<>();
        int totalPag = pageable.getPageSize();
        // 중요공지 (imptYn이 1인 공지)를 먼저 추가
        List<NoticeEntity> importantNotices = noticeRepository.findByImptYnAndSchoolEntitySchoolId(1L, userSchoolId);
        for (NoticeEntity entity : importantNotices) {

                result.add(NoticeTotalVo.builder()
                        .noticeId(entity.getNoticeId())
                        .title(entity.getTitle())
                        .imptYn(entity.getImptYn())
                        .hits(entity.getHits())
                        .createdAt(entity.getCreatedAt())
                        .content(entity.getContent())
                        .schoolId(entity.getSchoolEntity().getSchoolId())
                        .build());
            }

        int remainingNotices = 14 - importantNotices.size();
        if (remainingNotices > 0 ) {
            pageable = PageRequest.of(page - 1, remainingNotices, sort);
            Page<NoticeEntity> noticeEntityPage = noticeRepository.findByImptYnNotAndSchoolEntitySchoolId(1,userSchoolId, pageable);

            for (NoticeEntity entity : noticeEntityPage) {
                SchoolEntity school = entity.getSchoolEntity();
                if (school.getSchoolId().equals(userSchoolId)) {
                    result.add(NoticeTotalVo.builder()
                            .noticeId(entity.getNoticeId())
                            .title(entity.getTitle())
                            .imptYn(entity.getImptYn())
                            .hits(entity.getHits())
                            .createdAt(entity.getCreatedAt())
                            .content(entity.getContent())
                            .schoolId(school.getSchoolId())
                            .build());
                }
            }
        }


        long total = noticeRepository.countBySchoolEntitySchoolId(userSchoolId);

        NoticeListVo list = NoticeListVo.builder()
                .list(result)
                .total(total)
                .totalPage((int)Math.ceil((double) total/totalPag))
                .build();

        return list;
    }

    //en dto ->
    public NoticeVo saveByNotice(NoticeInsDto dto) {
        MyUserDetails userDetails = facade.getLoginUser();
        SchoolEntity schoolEntity = schoolRepository.getReferenceById(userDetails.getSchoolId());

        NoticeEntity entity = NoticeEntity.builder().title(dto.getTitle()).content(dto.getContent())
                .imptYn(dto.getImptyn()).schoolEntity(schoolEntity).build();

        NoticeEntity result = noticeRepository.save(entity);

        return NoticeVo.builder()
                .noticeId(result.getNoticeId())
                .title(result.getTitle())
                .content(result.getContent())
                .createdAt(result.getCreatedAt())
                .imptYn(result.getImptYn())
                .hits(result.getHits())
                .userId(userDetails.getUserId())
                .build();
    }

    public NoticeVo findBySchoolNotice(Long noticeId) {
        MyUserDetails userDetails = facade.getLoginUser();

        NoticeEntity sel = noticeRepository.findByNoticeId(noticeId);

        NoticeVo vo = NoticeVo.builder()
                .noticeId(sel.getNoticeId())
                .title(sel.getTitle())
                .content(sel.getContent())
                .createdAt(sel.getCreatedAt())
                .userId(userDetails.getUserId())
                .imptYn(sel.getImptYn())
                .hits(sel.getHits())
                .build();

        return vo;
    }

    public NoticeVo2 upNotice(NoticePatchDto dto) {
        MyUserDetails userDetails = facade.getLoginUser();
        SchoolEntity schoolEntity = schoolRepository.getReferenceById(userDetails.getSchoolId());
        NoticeEntity entity = NoticeEntity.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .noticeId(dto.getNoticeId())
                .imptYn(dto.getImptyn())
                .schoolEntity(schoolRepository.getReferenceById(schoolEntity.getSchoolId()))
                .build();

        NoticeEntity result = noticeRepository.save(entity);

        return NoticeVo2.builder()
                .title(result.getTitle())
                .content(result.getContent())
                .noticeId(result.getNoticeId())
                .imptYn(result.getImptYn())
                .build();
    }

    public void delNotice(Long noticeId) {
        NoticeEntity entity = noticeRepository.getReferenceById(noticeId);
        noticeRepository.delete(entity);
    }

    public int upHits(Long noticeId) {
        Optional<NoticeEntity> opt = noticeRepository.findById(noticeId);
        if (opt.isEmpty()) {
            return 0;
        }

        NoticeEntity entity = opt.get();
        entity.setHits(entity.getHits() + 1);
        noticeRepository.save(entity);
        return entity.getHits();
    }


    public NoticeListVo2 searchNotice(String search, int page) {
        MyUserDetails userDetails = facade.getLoginUser();
        Long userSchoolId = userDetails.getSchoolId();

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, 14, sort);
        List<NoticeTotalVo> result = new ArrayList<>();
        int totalPag = pageable.getPageSize();
        // 중요공지 (imptYn이 1인 공지)를 먼저 추가
        List<NoticeEntity> importantNotices = noticeRepository.findByImptYnAndSchoolEntitySchoolId(1L, userSchoolId);
        for (NoticeEntity entity : importantNotices) {
            result.add(NoticeTotalVo.builder()
                    .noticeId(entity.getNoticeId())
                    .title(entity.getTitle())
                    .imptYn(entity.getImptYn())
                    .hits(entity.getHits())
                    .createdAt(entity.getCreatedAt())
                    .content(entity.getContent())
                    .schoolId(entity.getSchoolEntity().getSchoolId())
                    .build());
        }

        int remainingNotices = 14 - importantNotices.size();
        if (remainingNotices > 0 && search != null) {
            pageable = PageRequest.of(page - 1, remainingNotices, sort);
            Page<NoticeEntity> noticeEntityPage = noticeRepository.findByTitleContainingAndImptYnNot(search, 1, pageable);

            for (NoticeEntity entity : noticeEntityPage) {
                SchoolEntity school = entity.getSchoolEntity();
                if (school.getSchoolId().equals(userSchoolId)) {
                    result.add(NoticeTotalVo.builder()
                            .noticeId(entity.getNoticeId())
                            .title(entity.getTitle())
                            .imptYn(entity.getImptYn())
                            .hits(entity.getHits())
                            .createdAt(entity.getCreatedAt())
                            .content(entity.getContent())
                            .schoolId(school.getSchoolId())
                            .build());
                }
            }
        }


        long total = noticeRepository.countBySchoolEntitySchoolId(userSchoolId);

        long totalSearch = 0L;
        long searchImptYn = 0L;
        long importantNoticesCount = 0L;  // 이 부분을 추가합니다.


            totalSearch = noticeRepository.countByTitleContainingAndImptYnNotAndSchoolEntitySchoolId
                    (search,  1L,  userSchoolId);

//        searchImptYn =noticeRepository.countByTitleContainingAndImptYnNotAndSchoolEntitySchoolId(search,0L,userSchoolId);
        searchImptYn =noticeRepository.countByImptYnAndSchoolEntitySchoolId(1L,userSchoolId);
        NoticeListVo2 list = NoticeListVo2.builder()
        .list(result)
        .total(total)
        .totalPage((int)Math.ceil((double) total/totalPag))
        .searchTotal(totalSearch+searchImptYn)
        .searchPage((long) Math.ceil((double) totalSearch/totalPag))
        .build();

        return list;
    }
}
