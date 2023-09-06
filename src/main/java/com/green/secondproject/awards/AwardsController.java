//package com.green.secondproject.awards;
//
//import com.green.secondproject.awards.model.AwardsInsDto;
//import com.green.secondproject.awards.model.AwardsVo;
//import com.green.secondproject.common.entity.AwardsEntity;
//import com.green.secondproject.volunteerwork.model.VolWorkInsDto;
//import com.green.secondproject.volunteerwork.model.VolWorkVo;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@Slf4j
//@RequestMapping("/api/awards")
//@RequiredArgsConstructor
//@Tag(name = "수상경력")
//public class AwardsController {
//    private final AwardsService service;
//
//
//
//    @PostMapping
//    @Operation(summary = "학생별 수상경력 입력"
//            , description = """
//                    입력값 :입력값 :<br> (1)userId : 입력대상학생pk <br>(2)nm : 대회이름 <br>
//                    (3)prize : 등급(위) <br>(4)awardedAt : 수상연월<br>(5)awardedBy : 수상기관 <br>
//                    (6)참가대상 : participation <br><br>
//                    출력값 : 입력요구값과 동일하나 정상반영시 awardId가 추가됩니다
//                    volunteerId : 수상경력 pk
//                    """)
//    public ResponseEntity<AwardsVo> postAward(@RequestBody AwardsInsDto dto){
//        AwardsVo vo = service.postAward(dto);
//        return ResponseEntity.ok(vo);
//    }
//
//    @GetMapping
//    @Operation(summary = "학생별 수상경력 조회 "
//            , description = """
//                    입력값 :<br> (1)userId : 입력대상학생pk <br>(2)nm : 대회이름 <br>
//                    (3)prize : 등급(위) <br>(4)aw력ardedAt : 수상연월<br>(5)awardedBy : 수상기관 <br>
//                    (6)참가대상 : participation <br>(7)volunteerId : 수상경력pk <br><br><br>
//
//
//                    """)
//    public ResponseEntity<List<AwardsVo>> getAwardsList(Long userId){
//        List<AwardsVo> vo = (userId);
//        return ResponseEntity.ok(vo);
//    }
//
//}
