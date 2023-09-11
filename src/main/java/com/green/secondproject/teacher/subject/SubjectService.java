package com.green.secondproject.teacher.subject;

import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.*;
import com.green.secondproject.common.repository.SbjCategoryRepository;
import com.green.secondproject.common.repository.UserRepository;
import com.green.secondproject.common.repository.VanRepository;
import com.green.secondproject.teacher.subject.model.graph.MockGraphDto;
import com.green.secondproject.teacher.subject.model.graph.MockGraphVo;
import com.green.secondproject.teacher.subject.model.graph.MockGraphVo2;
import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.repository.SubjectRepository;
import com.green.secondproject.teacher.subject.model.*;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Service
@ToString
@RequiredArgsConstructor
@Slf4j
public class SubjectService {
    private final SubjectMapper mapper;
    private final AuthenticationFacade facade;
    private final SubjectRepository subjectRepository;
    private final VanRepository vanRep;
    private final UserRepository userRepository;
    private final SbjCategoryRepository sbjCategoryRepository;



    public int classnum(StudentClassDto dto) {
        return mapper.classnum(dto);
    }

    public int schoolnum(@AuthenticationPrincipal MyUserDetails user) {
        List<VanEntity> vanList = vanRep.findAllBySchoolEntityAndGradeAndYear(
                SchoolEntity.builder().schoolId(user.getSchoolId()).build(), user.getGrade(),
                String.valueOf(LocalDate.now().getYear()));
       List<UserEntity> list = userRepository.findAllByVanEntityInAndRoleType(vanList, RoleType.STD);

       return list.size();

//        StudentSchoolDto dto = new StudentSchoolDto();
//        dto.setGrade(user.getGrade());
//        dto.setSchoolNm(user.getSchoolNm());
//        return mapper.schoolnum(dto);
    }

public List<MockSubjcetSmallVo> mocksmalllist(Long categoryid) {
    SbjCategoryEntity sbjCategoryEntity= SbjCategoryEntity.builder()
            .categoryId(categoryid)
            .build();

       List<SubjectEntity> subjectList = subjectRepository.findBySbjCategoryEntity(sbjCategoryEntity);
       List<MockSubjcetSmallVo> list = new ArrayList<>();

    for (SubjectEntity subjectEntity : subjectList) {
        list.add(MockSubjcetSmallVo.builder()
                .categoryid(subjectEntity.getSbjCategoryEntity().getCategoryId())
                .subjectid(subjectEntity.getSubjectId())
                .nm(subjectEntity.getNm())
                .build());
    }
    return list;
//
//        return subjectList.stream().map(subjectEntity -> MockSubjcetSmallVo.builder()
//                .categoryid(subjectEntity.getSbjCategoryEntity().getCategoryId())
//                .subjectid(subjectEntity.getSubjectId())
//                .nm(subjectEntity.getNm())
//                .build()).toList();
    }

    public List<MockSubjectBigVo> mockbiglist() {
        return mapper.mockbiglist();
    }

    public List<StudentListVo> stulist(StudentListDto dto){
       return mapper.stulist(dto);
    };

   //=================================3차 과목관리 관리자 권한이전으로 인해 삭제된 부분 ==================================================
//   public List<SubjectVo> tcslist() {
//       MyUserDetails myuser = facade.getLoginUser();
//       String grade = vanRep.findByVanId(myuser.getVanId()).getGrade();
//       Long schoolId = myuser.getSchoolId();
//       return mapper.tcslist(grade,schoolId);
//   }
//
//   public List<SubjectVo3> smallList(Long categoryId) {
//       MyUserDetails myuser = facade.getLoginUser();
//       String grade = vanRep.findByVanId(myuser.getVanId()).getGrade();
//       Long schoolId = myuser.getSchoolId();
//        return mapper.smallList(categoryId,grade,schoolId);
//    }
//
//    //    public List<SubjectVo> subcate() {
////        return mapper.subCate();
////    }
//    public List<SubjectVo> subcate(){
//        MyUserDetails userDetails = facade.getLoginUser();//학교마다 과목안달라지면 안해도됨이거 ㅇㅇ 혹시모르니 추가
//        Long userSchoolId = userDetails.getSchoolId();
//        List<SubjectVo> result = new ArrayList<>();
//        List<SbjCategoryEntity> list = sbjCategoryRepository.findByType(1);
//
//        for (SbjCategoryEntity e : list) {
//            result.add(SubjectVo.builder()
//                    .nm(e.getNm())
//                    .categoryId(e.getCategoryId())
//                    .build());
//        }
//        return result;
//    }
//
//
//    public int instcsbj(SubjectInsDto2 dto) {
//        List<SubjectInsVo> list = new LinkedList<>();
//
//        for (int i = 0; i < dto.getList().size(); i++) {
//            SubjectInsVo vo = new SubjectInsVo();
//            vo.setSubjectid(dto.getList().get(i).getSubjectId());
//            vo.setUserid(facade.getLoginUserPk());
//            list.add(vo);
//        }
//
//        return mapper.instcsbj(list);
//    }

//    public List<SubjectDetailVo> subject(Long categoryid) {
//        return mapper.subject(categoryid);
//    }

}

