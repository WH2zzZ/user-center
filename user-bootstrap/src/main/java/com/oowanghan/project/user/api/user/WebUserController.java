package com.oowanghan.project.user.api.user;

import cn.hutool.core.util.StrUtil;
import com.oowanghan.atlantis.framework.auth.annotation.AuthControl;
import com.oowanghan.atlantis.framework.auth.entity.AtlantisJwtClaim;
import com.oowanghan.atlantis.framework.common.exception.BizErrorCode;
import com.oowanghan.atlantis.framework.common.exception.BizException;
import com.oowanghan.atlantis.framework.web.vo.BizResponse;
import com.oowanghan.project.user.api.user.convert.UserAppVoConvert;
import com.oowanghan.project.user.api.user.vo.UserLoginVo;
import com.oowanghan.project.user.api.user.vo.UserVo;
import com.oowanghan.project.user.service.user.UserService;
import com.oowanghan.project.user.service.user.bo.UserBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 用户相关信息
 *
 * @author wanghan
 */
@RestController
@RequestMapping("/api/web/user")
public class WebUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login/verifycode")
    public BizResponse<Void> getLoginVerifyCode(@RequestParam String mobile) {
        if (StrUtil.isBlank(mobile) || mobile.length() != 11) {
            throw new BizException(BizErrorCode.PARAM_ERROR, "手机号码格式错误");
        }
        userService.getLoginVerificationCode(mobile);
        return BizResponse.success();
    }

    @PostMapping("/login")
    public BizResponse<Void> login(@Validated @RequestBody UserLoginVo request,
                                   HttpServletResponse servletResponse) {
        String token = userService.login(UserAppVoConvert.INSTANCE.convertUserLoginVoToUserLoginBo(request));
        servletResponse.setHeader(AtlantisJwtClaim.AUTHORIZATION, "Bearer " + token);
        return BizResponse.success();
    }

    @PostMapping("/logout")
    @AuthControl
    public BizResponse<Void> logout(@RequestParam String mobile,
                                    HttpServletRequest request) {
        if (StrUtil.isBlank(mobile)) {
            throw new BizException(BizErrorCode.PARAM_ERROR);
        }
        userService.logout(mobile, request.getHeader(AtlantisJwtClaim.AUTHORIZATION));
        return BizResponse.success();
    }

    @AuthControl
    @GetMapping("/info")
    public BizResponse<UserVo> getUser(@RequestParam String mobile) {
        if (StrUtil.isBlank(mobile)) {
            throw new BizException(BizErrorCode.PARAM_ERROR);
        }
        UserBo userBo = userService.getUserByMobile(mobile);

        return BizResponse.success(UserAppVoConvert.INSTANCE.convertUserBoToUserVo(userBo));
    }

    @PutMapping("/nickname")
    @AuthControl
    public BizResponse<Void> updateNickname(@RequestParam("nickName") String nickName) {
        userService.updateNickname(nickName);
        return BizResponse.success();
    }

    @PutMapping("/headimage")
    @AuthControl
    public BizResponse<Void> putHeadImage(@RequestParam("imageUrl") String imageUrl) {
        userService.updateHeadImage(imageUrl);
        return BizResponse.success();
    }

}
