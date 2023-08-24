package com.green.secondproject.notice;

import com.green.secondproject.common.entity.NoticeEntity;
import com.green.secondproject.common.entity.SchoolEntity;
import com.green.secondproject.common.repository.NoticeRepository;
import com.green.secondproject.notice.model.NoticeSelDto;
import com.green.secondproject.notice.model.NoticeVo;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
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

    public List<NoticeVo> noticeList() {
        NoticeSelDto dto = new NoticeSelDto();

        dto.setSchoolId(1L);
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
}
