package com.green.secondproject.mypage;

import com.green.secondproject.config.security.AuthenticationFacade;
import com.green.secondproject.config.security.model.MyUserDetails;
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
    private final AuthenticationFacade facade;

    @Value("/home/download")
    private String fileDir;

    public int updUserPw(UpdUserPwDto dto) {
        return mapper.updUserPw(dto);
    }

    public List<SelUserMyPageVo> selUserMyPage(MyUserDetails myuser) {
        SelUserMyPageDto dto = new SelUserMyPageDto();
        dto.setUserId(myuser.getUserId());
        return mapper.selUserMyPage(dto);
    }


    public int updTeacherInfo(UpdTeacherInfoDto dtoNoPk) {
        UpdTeacherInfoDto2 dto = new UpdTeacherInfoDto2();
        dto.setNm(dtoNoPk.getNm());
        dto.setAddress(dtoNoPk.getAddress());
        dto.setPhone(dtoNoPk.getPhone());
        dto.setUserId(facade.getLoginUserPk());
        return mapper.updTeacherInfo(dto);
    }


    public int updStudentInfo(UpdStudentInfoDto dtoNoPk) {
        //여기서 dto에 userId값 받아오기
        UpdStudentInfoDto2 dto = new UpdStudentInfoDto2();
        dto.setNm(dtoNoPk.getNm());
        dto.setAddress(dtoNoPk.getAddress());
        dto.setPhone(dtoNoPk.getPhone());
        dto.setUserId(facade.getLoginUserPk());
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
