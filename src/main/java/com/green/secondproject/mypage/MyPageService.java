package com.green.secondproject.mypage;

import com.green.secondproject.common.config.security.AuthenticationFacade;
import com.green.secondproject.common.config.security.model.MyUserDetails;
import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.mypage.model.*;
import com.green.secondproject.common.utils.MyFileUtils;
import com.green.secondproject.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.StringUtils;

import java.io.File;
import java.util.Optional;

import static com.green.secondproject.common.config.etc.EnrollState.*;

@Slf4j
@Service
@RequiredArgsConstructor
@ToString
public class MyPageService {
    private final MyPageMapper mapper;
    private final AuthenticationFacade facade;
    private final PasswordEncoder PW_ENCODER;
    private final UserRepository userRepository;


    @Value("/home/download")
    private String fileDir;

    @Value("${file.imgPath}")
    private String imgPath;


    public SelUserMyPageVo selUserMyPage(MyUserDetails myuser) {
        Optional<UserEntity> opt = userRepository.findById(myuser.getUserId());
        if (opt.isEmpty()) {
            throw new RuntimeException("존재하지 않는 사용자");
        }

        UserEntity entity = opt.get();
        return SelUserMyPageVo.builder()
                .userId(entity.getUserId())
                .unm(entity.getNm())
                .email(entity.getEmail())
                .role(entity.getRoleType().getCode())
                .pic(String.format("%s/%d/%s", imgPath, entity.getUserId(), entity.getPic()))
                .birth(entity.getBirth())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .detailAddr(entity.getDetailAddr())
                .classId(entity.getVanEntity().getVanId())
                .grade(entity.getVanEntity().getGrade())
                .van(entity.getVanEntity().getClassNum())
                .schoolId(entity.getVanEntity().getSchoolEntity().getSchoolId())
                .schnm(entity.getVanEntity().getSchoolEntity().getNm())
                .build();

//        SelUserMyPageDto dto = new SelUserMyPageDto();
//        dto.setUserId(myuser.getUserId());
//
//        SelUserMyPageVo userInfo = mapper.selUserMyPage(dto);
//        userInfo.setPic(String.format("%s/%d/%s", imgPath, myuser.getUserId(), userInfo.getPic()));
//        return userInfo;
    }


    public int updUserInfo(MultipartFile pic, UpdInfoParam p, MyUserDetails myuser) {
        String centerPath = String.format("hiSchool/userPic/%d", myuser.getUserId());
        String dicPath = String.format("%s/%s", MyFileUtils.getAbsolutePath(fileDir), centerPath);

        if (pic != null) {
            File dic = new File(dicPath);
            if (!dic.exists()) {
                dic.mkdirs();
            }


            String OriginalFileName = myuser.getPic();
            if (OriginalFileName != null) {
                File originFile = new File(dic, OriginalFileName);
                if (originFile.exists()) {
                    originFile.delete();
                }
            }

            String originFileName = pic.getOriginalFilename();
            String savedFileName = MyFileUtils.makeRandomFileNm(originFileName);
            String savedFilePath = String.format("%s/%s", centerPath, savedFileName);
            String targetPath = String.format("%s/%s", MyFileUtils.getAbsolutePath(fileDir), savedFilePath);
            File target = new File(targetPath);

            try {
                pic.transferTo(target);
            } catch (Exception e) {
                return 0;
            }


//            if(!StringUtils.isEmpty(myuser.getPw()) && !myuser.getPw().equals(myuser.getPw())) {
//                myuser.setPw(myuser.getPw());
//            }
            try {
                if(!StringUtils.isEmpty(myuser.getPw()) && !myuser.getPw().equals(myuser.getPw())) {
                    myuser.setPw(myuser.getPw());
                }
                UserUpdDto dto = UserUpdDto.builder()
                        .phone(p.getPhone())
                        .address(p.getAddress())
                        .detailAddr(p.getDetailAddr())
                        .pw(PW_ENCODER.encode(p.getPw()))
                        .userId(myuser.getUserId())
                        .pic(savedFileName)
                        .build();

                int result = mapper.updUserInfo(dto);
                if (result == 0) {
                    throw new Exception("프로필 사진을 등록할 수 없습니다.");
                }
            } catch (Exception e) {
                target.delete();
                return 0;
            }
        } else {
            try {
                if(!StringUtils.isEmpty(myuser.getPw()) && !myuser.getPw().equals(myuser.getPw())) {
                    myuser.setPw(myuser.getPw());
                }
                UserUpdDto dto = UserUpdDto.builder()
                        .phone(p.getPhone())
                        .address(p.getAddress())
                        .detailAddr(p.getDetailAddr())
                        .pw(PW_ENCODER.encode(p.getPw()))
                        .userId(myuser.getUserId())
                        .build();

                int result = mapper.updUserInfo(dto);


                File dic = new File(dicPath);
                String OriginalFileName = myuser.getPic();
                if (OriginalFileName != null) {
                    File originFile = new File(dic, OriginalFileName);
                    if (originFile.exists()) {
                        originFile.delete();
                    }
                }
            } catch (Exception e) {
                return 0;
            }
        }
        return 1;
    }

//    @Transactional
//    public int updUserInfo(MultipartFile pic, UpdInfoParam p, MyUserDetails myuser) {
//        try {
//            UserEntity userEntity = userRepository.findByUserId(myuser.getUserId());
//
//            if (userEntity == null) {
//                return 0; // 사용자가 존재하지 않는 경우 처리
//            }
//
//            userEntity.setPhone(p.getPhone());
//            userEntity.setAddress(p.getAddress());
//            userEntity.setDetailAddr(p.getDetailAddr());
//
//            if (!StringUtils.isEmpty(p.getPw())) {
//                // 새로운 비밀번호를 해시하고 설정
//                userEntity.setPw(PW_ENCODER.encode(p.getPw()));
//            }
//
//            if (pic != null) {
//                // 프로필 사진 업데이트 처리
//                String savedFileName = MyFileUtils.makeRandomFileNm(pic.getOriginalFilename());
//                userEntity.setPic(savedFileName);
//
//                // 업데이트된 사용자 엔티티 저장
//                userRepository.save(userEntity);
//            } else {
//                // 프로필 사진을 업데이트하지 않고 사용자 엔티티 저장
//                userRepository.save(userEntity);
//
//                // 사진 파일이 없는 경우 기존 파일을 삭제합니다.
//                String centerPath = String.format("hiSchool/userPic/%d", myuser.getUserId());
//                String dicPath = String.format("%s/%s", MyFileUtils.getAbsolutePath(fileDir), centerPath);
//                String OriginalFileName = myuser.getPic();
//                if (OriginalFileName != null) {
//                    File originFile = new File(dicPath, OriginalFileName);
//                    if (originFile.exists()) {
//                        originFile.delete();
//                    }
//                }
//            }
//
//            return 1;
//        } catch (Exception e) {
//            // 예외 처리를 여기서 수행하세요
//            return 0;
//        }
//    }


    public int pwCheck(PwDto dto) {
        Optional<UserEntity> opt = userRepository.findById(facade.getLoginUserPk());
        if (opt.isEmpty()) {
            throw new RuntimeException("유효하지 않은 사용자");
        }

        UserEntity entity = opt.get();
        return PW_ENCODER.matches(dto.getPw(), entity.getPw()) ? 1 : 0;
    }
}