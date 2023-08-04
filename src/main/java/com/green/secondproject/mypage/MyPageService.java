package com.green.secondproject.mypage;

import com.green.secondproject.mypage.model.*;
import com.green.secondproject.utils.MyFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MyPageMapper mapper;
    private final PasswordEncoder PW_ENCODER;

    @Value("/home/download")
    private String fileDir;

    public int updUserPw(UpdUserPwDto dto) {
        return mapper.updUserPw(dto);
    }

    public List<SelUserMyPageVo> selUserMyPage(Long userId) {
        SelUserMyPageDto dto = new SelUserMyPageDto();
        dto.setUserId(userId);
        return mapper.selUserMyPage(dto);
    }


    public int UpdTeacherInfo(UpdTeacherInfoDto dto) {
        return mapper.updTeacherInfo(dto);
    }


    public int UpdStudentInfo(UpdStudentInfoDto dto) {
        return mapper.updStudentInfo(dto);
    }


    public String updUserPic(MultipartFile pic, UserPicDto dto) {
        String temp = "0";
        String centerPath = String.format("user/%d", dto.getUserId());
        String dicPath = String.format("%s/%s", MyFileUtils.getAbsolutePath(fileDir), centerPath);

        File dic = new File(dicPath);
        if (!dic.exists()) {
            dic.mkdirs();
        }

        String originFileName = pic.getOriginalFilename();
        String savedFileName = MyFileUtils.makeRandomFileNm(originFileName);
        String savedFilePath = String.format("%s/%s", centerPath, savedFileName);
        String targetPath = String.format("%s/%s", MyFileUtils.getAbsolutePath(fileDir), savedFilePath);
        File target = new File(targetPath);
        try {
            pic.transferTo(target);
        }catch (Exception e) {
            return temp;
        }
        dto.setPic(savedFilePath);
        try {
            int result = mapper.updUserPic(dto);
            if(result == Integer.parseInt(temp)) {
                throw new Exception("프로필 사진을 등록할 수 없습니다.");
            }
        } catch (Exception e) {
            target.delete();
            return temp;
        }
        return savedFilePath;
    }
}
