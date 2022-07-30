package com.oowanghan.project.user.dao.user.repository;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oowanghan.atlantis.framework.web.vo.PageResult;
import com.oowanghan.project.user.dao.user.entity.UserEntity;
import com.oowanghan.project.user.dao.user.entity.UserSearchEntity;
import com.oowanghan.project.user.dao.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author WangHan
 * @Create 2021/3/9 12:11 上午
 */
@Slf4j
@Repository
public class UserRepository {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Snowflake snowflake;

    public UserEntity getByMobile(String mobile) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new QueryWrapper<UserEntity>()
                .lambda()
                .eq(UserEntity::getMobile, mobile);
        UserEntity userEntity = userMapper.selectOne(queryWrapper);
        return userEntity;
    }

    public Long insert(UserEntity userEntity) {
        long id = snowflake.nextId();
        userEntity.setId(id);
        userMapper.insert(userEntity);
        return id;
    }

    public void resetPassword(long id, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setPassword(password);
        userMapper.updateById(userEntity);
    }

    public UserEntity getById(Long uid) {
        return userMapper.selectById(uid);
    }

    public void update(UserEntity oldUserEntity) {
        userMapper.updateById(oldUserEntity);
    }

    public PageResult<UserEntity> getPage(UserSearchEntity searchEntity, int page, int pageSize) {
        LambdaQueryWrapper<UserEntity> lambda = makeWrapper(searchEntity);
        Page<UserEntity> pageTool = new Page<>(page, pageSize);

        Page<UserEntity> userEntityPage = userMapper.selectPage(pageTool, lambda.orderByAsc(UserEntity::getCreateTime));

        return PageResult.instance(userEntityPage.getRecords(), userEntityPage.getTotal());
    }

    private LambdaQueryWrapper<UserEntity> makeWrapper(UserSearchEntity searchBo) {
        LambdaQueryWrapper<UserEntity> lambda = new QueryWrapper<UserEntity>().lambda();
        if (searchBo == null) {
            return lambda;
        }
        lambda.eq(StrUtil.isNotBlank(searchBo.getMobile()), UserEntity::getMobile, searchBo.getMobile())
                .like(StrUtil.isNotBlank(searchBo.getUsername()), UserEntity::getUsername,
                        searchBo.getUsername())
                .between(searchBo.getStartTime() != null && searchBo.getEndTime() != null,
                        UserEntity::getCreateTime, searchBo.getStartTime(), searchBo.getEndTime())
                .eq(searchBo.getId() != null, UserEntity::getId, searchBo.getId());
        return lambda;
    }
}
