package com.green.secondproject.aprpic;

import com.green.secondproject.common.entity.UserEntity;
import com.green.secondproject.common.repository.UserRepository;
import com.green.secondproject.common.utils.MyFileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AprPicService {
    private final UserRepository rep;

    @Value("${file.dir}")
    private String fileDir;

    public UserEntity updAprPic(Long userId, MultipartFile aprPic) {
        fileDir = MyFileUtils.getAbsolutePath(fileDir);

        String aprPicDicPath = fileDir + "/hiSchool/userApr/" + userId;
        String savedPicNm = MyFileUtils.makeRandomFileNm(aprPic.getOriginalFilename());

        File aprPicDic = new File(aprPicDicPath);
        if (!aprPicDic.exists()) { aprPicDic.mkdirs(); }

        File targetAprPic = new File(aprPicDic, savedPicNm);
        try {
            aprPic.transferTo(targetAprPic);
        } catch (IOException e) {
            e.printStackTrace();
        }

        UserEntity user = rep.findById(userId).get();
        user.setAprPic(savedPicNm);
        return rep.save(user);
    }
}
