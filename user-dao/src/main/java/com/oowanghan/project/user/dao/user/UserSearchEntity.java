package com.oowanghan.project.user.dao.user.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author WangHan
 * @Create 2021/3/24 6:08 下午
 */
@Data
public class UserSearchEntity {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String mobile;

    private String username;

    private Long id;
}
