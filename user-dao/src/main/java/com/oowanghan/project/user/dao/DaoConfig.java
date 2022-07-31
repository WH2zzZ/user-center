package com.oowanghan.project.user.dao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author WangHan
 * @Create 2022/7/31 14:37
 */
@Configuration
@MapperScan(basePackages = {"com.oowanghan.project.user.dao.*.mapper"})
public class DaoConfig {
}
