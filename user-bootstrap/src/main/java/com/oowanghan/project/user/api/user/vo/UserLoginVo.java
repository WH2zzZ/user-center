package com.oowanghan.project.user.api.user.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginVo {

    @NotBlank
    private String mobile;

    @NotBlank
    private String verifyCode;
}