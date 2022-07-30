package com.oowanghan.project.user.service.user.convert;

import com.oowanghan.project.user.dao.user.entity.UserEntity;
import com.oowanghan.project.user.dao.user.entity.UserSearchEntity;
import com.oowanghan.project.user.service.user.bo.UserBo;
import com.oowanghan.project.user.service.user.bo.UserSearchBo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserBoConvert {

    UserBoConvert INSTANCE = Mappers.getMapper(UserBoConvert.class);

    UserBo convertUserEntityToUserBo(UserEntity source);

    List<UserBo> toUserBoList(List<UserEntity> userEntities);

    UserSearchEntity convertToUserSearchEntity(UserSearchBo searchBo);
}