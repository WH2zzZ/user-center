package com.oowanghan.project.user.service.user.bo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author WangHan
 * @Create 2021/3/24 6:08 下午
 */
@Data
public class UserSearchBo {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String mobile;

    private String username;

    private Long id;
}
