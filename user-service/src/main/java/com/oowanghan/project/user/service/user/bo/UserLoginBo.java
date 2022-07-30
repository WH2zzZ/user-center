package com.oowanghan.project.user.service.user.bo;

import lombok.Data;

@Data
public class UserLoginBo {

    private String mobile;

    private String verifyCode;
}