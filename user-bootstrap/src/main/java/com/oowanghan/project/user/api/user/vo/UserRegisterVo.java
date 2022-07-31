package com.oowanghan.project.user.api.user.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterVo {

    @NotBlank
    private String mobile;

    @NotBlank
    private String verifyCode;
}