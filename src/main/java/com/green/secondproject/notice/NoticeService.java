package com.green.secondproject.notice;

import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.entity.NoticeEntity;
import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.repository.NoticeRepository;
import com.green.secondproject.common.repository.SchoolRepository;
import com.green.secondproject.notice.model.*;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<NoticeVo> noticeList() {
        NoticeSelDto dto = new NoticeSelDto();
        MyUserDetails userDetails = facade.getLoginUser();

        SchoolEntity entityschool = schoolRepository.getReferenceById(userDetails.getSchoolId());
        dto.setSchoolId(entityschool.getSchoolId());
        SchoolEntity schoolEntity = SchoolEntity.builder()
                .schoolId(dto.getSchoolId())
                .build();

        List<NoticeEntity> noticeList = noticeRepository.findByschoolEntity(schoolEntity);
        List<NoticeVo> list = new ArrayList<>();


        for (NoticeEntity entity : noticeList) {
            list.add(NoticeVo.builder()
                    .noticeId(entity.getNoticeId())
                    .title(entity.getTitle())
                    .content(entity.getContent())
                    .createdAt(entity.getCreatedAt())
                    .userId(userDetails.getUserId())
                    .imptYn(entity.getImptYn())
                    .hits(entity.getHits()).build());
        }
        return list;
    }
    //en dto ->
    public NoticeVo saveByNotice(NoticeInsDto dto){
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

    public NoticeVo findBySchoolNotice(Long noticeId){
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
    public NoticeVo2 upNotice(NoticePatchDto dto){
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
                .imptyn(result.getImptYn())
                .build();
    }
    public void delNotice(Long noticeId){
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
}
