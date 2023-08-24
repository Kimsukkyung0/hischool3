package com.green.secondproject.notice;

import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.entity.NoticeEntity;
import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.repository.NoticeRepository;
import com.green.secondproject.common.repository.SchoolRepository;
import com.green.secondproject.notice.model.NoticeInsDto;
import com.green.secondproject.notice.model.NoticeSelDto;
import com.green.secondproject.notice.model.NoticeVo;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                .hits(result.getHits())
                .build();
    }

}
