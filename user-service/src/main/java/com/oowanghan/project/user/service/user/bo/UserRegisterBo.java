package com.oowanghan.project.user.service.user.bo;

import cn.hutool.crypto.SecureUtil;
import com.oowanghan.project.user.common.GenderTypeEnum;
import com.oowanghan.project.user.dao.user.entity.UserEntity;
import lombok.Data;

@Data
public class UserRegisterBo {

    private String mobile;

    private String password;

    private String verifyCode;

    public UserEntity convertToUserEntity(UserRegisterBo request) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(request.getMobile());
        userEntity.setPassword(SecureUtil.md5(request.getPassword()));
        userEntity.setMobile(request.getMobile());
        userEntity.setNickName(request.getMobile());
        userEntity.setImageUrl("");
        userEntity.setGender(GenderTypeEnum.UNKNOW.getType());
        return userEntity;
    }
}