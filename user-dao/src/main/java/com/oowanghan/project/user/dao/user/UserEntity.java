package com.oowanghan.project.user.dao.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.oowanghan.atlantis.framework.mysql.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wanghan
 * @since 2021-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("iot_user")
public class UserEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
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

    /**
     * 地址
     */
    private String address;

    /**
     * 状态
     */
    private Integer status;

}
