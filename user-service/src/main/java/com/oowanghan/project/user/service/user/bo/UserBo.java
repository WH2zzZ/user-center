package com.oowanghan.project.user.service.user.bo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * @author wanghan
 * @since 2021-03-04
 */
@Data
public class UserBo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 微信号
     */
    private String wechatId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 地址
     */
    private String address;

    /**
     * 头像
     */
    private String imageUrl;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别 1：男 2：女 0：保密
     */
    private Integer gender;

    /**
     * 生日 13位时间戳
     */
    private Integer birthday;
    private Long deviceId;
    private Integer skinId;

    /**
     * 状态
     */
    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String creator;

    private String updator;

}
