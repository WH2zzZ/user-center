package com.oowanghan.project.user.api.vo;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author wanghan
 * @since 2021-03-04
 */
@Data
public class UserVo {

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
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String imageUrl;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 性别 1：男 2：女 0：保密
     */
    private Integer gender;

    /**
     * 生日 13位时间戳
     */
    private Integer birthday;

    /**
     * 状态
     */
    private Integer status;

}
