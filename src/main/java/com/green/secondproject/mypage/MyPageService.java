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
import org.springframework.web.multipart.MultipartFile;
import reactor.util.StringUtils;

import java.io.File;

import static com.green.secondproject.common.config.etc.EnrollState.GRADUATION;

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
        SelUserMyPageDto dto = new SelUserMyPageDto();
        dto.setUserId(myuser.getUserId());

        SelUserMyPageVo userInfo = mapper.selUserMyPage(dto);
        userInfo.setPic(String.format("%s/%d/%s", imgPath, myuser.getUserId(), userInfo.getPic()));
        return userInfo;
    }


    public int delUser(MyUserDetails myuser) {
//        DelUserDto dto = new DelUserDto();
//        dto.setUserId(myuser.getUserId());
//        return mapper.delUser(dto);

        Long userId = myuser.getUserId();
        UserEntity userEntity = userRepository.findByUserId(userId);
        userEntity.setEnrollState(GRADUATION);

        userRepository.save(userEntity);

        return 1;
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
}