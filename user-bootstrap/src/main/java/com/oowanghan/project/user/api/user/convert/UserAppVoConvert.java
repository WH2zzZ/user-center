package com.oowanghan.project.user.api.user.convert;

import com.oowanghan.atlantis.util.tool.convert.LocalDateTimeConvert;
import com.oowanghan.project.user.service.user.bo.UserBo;
import com.oowanghan.project.user.service.user.bo.UserLoginBo;
import com.oowanghan.project.user.api.user.vo.UserLoginVo;
import com.oowanghan.project.user.api.user.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Lwx
 * @description 用户模块Vo对象Mapper
 * @date 2021-03-11 10:02
 */
@Mapper
public interface UserAppVoConvert extends LocalDateTimeConvert {

    UserAppVoConvert INSTANCE = Mappers.getMapper(UserAppVoConvert.class);

    UserLoginBo convertUserLoginVoToUserLoginBo(UserLoginVo source);

    UserVo convertUserBoToUserVo(UserBo userBo);
}
