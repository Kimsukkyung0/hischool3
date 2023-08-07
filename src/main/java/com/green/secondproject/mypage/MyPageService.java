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
        // 비밀번호 확인
        // 확인 후 설정한 비밀번호 암호화해서 저장 흠

        return mapper.updUserPw(dto);
    }

    public List<SelUserMyPageVo> selUserMyPage(MyUserDetails myuser) {
        SelUserMyPageDto dto = new SelUserMyPageDto();
        dto.setUserId(myuser.getUserId());
        return mapper.selUserMyPage(dto);
    }


    public int updTeacherInfo(UpdTeacherInfoDto dto) {
        UpdTeacherInfoDto2 dto2 = new UpdTeacherInfoDto2();
        dto2.setNm(dto.getNm());
        dto2.setAddress(dto.getAddress());
        dto2.setPhone(dto.getPhone());
        dto2.setUserId(facade.getLoginUserPk());
        return mapper.updTeacherInfo(dto2);
    }


    public int updStudentInfo(UpdStudentInfoDto dto) {
        UpdStudentInfoDto2 dto2 = new UpdStudentInfoDto2();
        dto2.setNm(dto.getNm());
        dto2.setAddress(dto.getAddress());
        dto2.setPhone(dto.getPhone());
        dto2.setUserId(facade.getLoginUserPk());
        return mapper.updStudentInfo(dto2);
    }



    public String updUserPic(MultipartFile pic, UserPicDto2 dto2) {
        UserPicDto dto = new UserPicDto();
        dto2.setPic(dto.getPic());
        dto2.setUserId(facade.getLoginUserPk());


        String temp = "0";
        String centerPath = String.format("user/%d", dto2.getUserId());
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
            int result = mapper.updUserPic(dto2);
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
