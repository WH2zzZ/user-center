package com.oowanghan.project.user.service.user;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.oowanghan.atlantis.framework.auth.JwtTokenCacheProcesser;
import com.oowanghan.atlantis.framework.auth.JwtTokenProcesser;
import com.oowanghan.atlantis.framework.auth.context.AuthContextHolder;
import com.oowanghan.atlantis.framework.common.exception.BizErrorCode;
import com.oowanghan.atlantis.framework.common.exception.BizException;
import com.oowanghan.atlantis.framework.redis.RedisKeyGenerater;
import com.oowanghan.atlantis.framework.web.vo.PageResult;
import com.oowanghan.project.user.dao.user.entity.UserEntity;
import com.oowanghan.project.user.dao.user.repository.UserRepository;
import com.oowanghan.project.user.service.message.SmsMessageService;
import com.oowanghan.project.user.service.user.bo.UserBo;
import com.oowanghan.project.user.service.user.bo.UserLoginBo;
import com.oowanghan.project.user.service.user.bo.UserRegisterBo;
import com.oowanghan.project.user.service.user.bo.UserSearchBo;
import com.oowanghan.project.user.service.user.convert.UserBoConvert;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户业务类
 *
 * @Author WangHan
 * @Create 2021/3/4 11:00 上午
 */
@Slf4j
@Service
public class UserService {

    public static final String AUTH_HEAD = "Bearer ";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SmsMessageService messageService;
    @Autowired
    private JwtTokenCacheProcesser jwtTokenCacheProcesser;
    @Autowired
    private JwtTokenProcesser jwtTokenProcesser;
    @Autowired
    private RedissonClient redissonClient;


    /**
     * 获取手机验证码
     *
     * @Author WangHan
     * @Create 11:30 上午 2021/3/4
     * @Param [mobile]
     * @Return java.lang.String
     */
    public void getLoginVerificationCode(String mobile) {
        RBucket<String> verifyCodeBucket = redissonClient.getBucket(getLoginVerifyCodeKey(mobile));
        if (StrUtil.isNotBlank(verifyCodeBucket.get())) {
            throw new BizException(BizErrorCode.REPEAT_REQUEST, "60s内无法获取验证码");
        }

        //进入主流程
        String verifyCode = String.valueOf(RandomUtil.randomInt(100000, 999999));
        log.debug("Sending verification code to '{}'", verifyCode);

        messageService.sendLoginSms(mobile, verifyCode);
        verifyCodeBucket.set(String.valueOf(verifyCode), 5L, TimeUnit.MINUTES);
    }

    /**
     * 登陆
     * @Author WangHan
     * @Create 19:40 2022/7/30
     * @Param [request]
     * @Return java.lang.String
     */
    public String login(UserLoginBo request) {
        boolean isRegister = checkMobileIsRegister(request.getMobile());
        if (!isRegister) {
            UserRegisterBo userRegisterBo = new UserRegisterBo();
            userRegisterBo.setMobile(request.getMobile());
            register(userRegisterBo);
        }

        return loginForVerifyCode(request);
    }

    public void register(UserRegisterBo request) {
        UserEntity userEntity = request.convertToUserEntity(request);
        userRepository.insert(userEntity);
    }

    public void logout(String mobile, String token) {

        boolean isRegister = checkMobileIsRegister(mobile);
        if (!isRegister) {
            throw new BizException(BizErrorCode.AUTH_ERROR);
        }

        if (!StringUtils.hasText(token) && token.startsWith(AUTH_HEAD)) {
            throw new BizException(BizErrorCode.AUTH_ERROR);
        }

        UserEntity userEntity = userRepository.getByMobile(mobile);
        jwtTokenCacheProcesser.logout(userEntity.getId(), token.substring(AUTH_HEAD.length()));
    }

    public UserBo getUserByMobile(String mobile) {
        UserEntity userEntity = userRepository.getByMobile(mobile);
        if (userEntity == null) {
            throw new BizException(BizErrorCode.PARAM_ERROR, "该人员不存在");
        }
        return UserBoConvert.INSTANCE.convertUserEntityToUserBo(userEntity);
    }


    private boolean checkMobileIsRegister(String mobile) {
        UserEntity userEntity = userRepository.getByMobile(mobile);
        if (userEntity == null) {
            return false;
        }
        return true;
    }

    private String loginForVerifyCode(UserLoginBo request) {
        RBucket<String> verifyCodeBucket = redissonClient.getBucket(
                getLoginVerifyCodeKey(request.getMobile()));
        if (StrUtil.isBlank(verifyCodeBucket.get())) {
            throw new BizException(BizErrorCode.VERIFY_CODE_EXPIRE);
        }
        if (!verifyCodeBucket.get().equals(request.getVerifyCode())) {
            throw new BizException(BizErrorCode.AUTH_ERROR);
        }

        UserEntity userEntity = userRepository.getByMobile(request.getMobile());

        return jwtTokenProcesser.createToken(userEntity.getId(), userEntity.getMobile(),
                userEntity.getUsername(), false);
    }

    private static String getLoginVerifyCodeKey(String request) {
        return RedisKeyGenerater.generateRedisKey("SMS", request);
    }

    public void updateNickname(String nickName) {
        UserEntity oldUserEntity = userRepository.getById(AuthContextHolder.getContext().getUid());
        oldUserEntity.setNickName(nickName);
        userRepository.update(oldUserEntity);
    }

    public void updateHeadImage(String imageUrl) {
        UserEntity oldUserEntity = userRepository.getById(AuthContextHolder.getContext().getUid());
        oldUserEntity.setImageUrl(imageUrl);
        userRepository.update(oldUserEntity);
    }

    /**
     * 分页查询会员
     *
     * @return
     * @description
     * @date 2021/6/14 12:48 下午
     */
    public PageResult<UserBo> getPage(UserSearchBo searchBo, int page, int pageSize) {
        PageResult<UserEntity> pageResult = userRepository.getPage(
                UserBoConvert.INSTANCE.convertToUserSearchEntity(searchBo),
                page,
                pageSize);
        List<UserBo> userBos = UserBoConvert.INSTANCE.toUserBoList(pageResult.getData());
        return PageResult.instance(userBos, pageResult.getTotal());
    }

    /**
     * 查询会员详情
     *
     * @description
     * @date 2021/6/14 12:50 下午
     */
    public UserBo getUserById(Long id) {
        return UserBoConvert.INSTANCE.convertUserEntityToUserBo(userRepository.getById(id));
    }
}
