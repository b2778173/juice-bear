package com.juice.common.utils;

import com.juice.common.constant.RedisKeyConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by ken on 2018/3/16.
 */
@Slf4j
@Component("tokenValidateUtils")
public class TokenValidateUtils {

    @Resource
    private RedisUtil redisUtil;

    private final static String LOGIN_KEY = "eJx%3ls8QNs7v!PmApISQFVSeYqLiYds";

    private final static String TWO_FACTOR_AUTH_LOGIN_KEY = "%6%Jn!!mnO1B@6wVaUV#AAjgOYLAYa^9";

    public boolean vlidateToken(String uid, String token) {
        String deToken = DESEncryptUtils.decrypt(token, LOGIN_KEY);

        String redisToken = redisUtil.get(RedisKeyConstant.REQ_TOKEN, uid);
        if (redisToken != null && redisToken.equals(deToken)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean valid2faLoginToken(String uid, String token) {
        String deToken = DESEncryptUtils.decrypt(token, TWO_FACTOR_AUTH_LOGIN_KEY);
        if (token.equals(deToken)) {
            return false;
        }

        String redisToken = redisUtil.get(RedisKeyConstant.TWO_FACTOR_AUTH_REQ_TOKEN, uid);
        return redisToken != null && redisToken.equals(deToken);
    }

    public boolean vlidateDeviceToken(String uid, String token) {
        String deToken = DESEncryptUtils.decrypt(token, LOGIN_KEY);

        if (!StringUtils.isNumeric(uid)) {
            return false;
        }

        String currentToken = getUserToken(Integer.valueOf(uid));
        if (!StringUtils.isEmpty(currentToken) && !deToken.equals(currentToken)) {
            // 在其它设备登录
            return true;
        }

        return false;
    }

    public String createUserToken(Integer userId) {
        String token = genToken();
        redisUtil.setExpire(RedisKeyConstant.REQ_TOKEN, token, 604800, userId);
        redisUtil.setExpire(RedisKeyConstant.REQ_LOGIN, userId, 604800, token);
        try {
            token = DESEncryptUtils.encrypt(token, LOGIN_KEY);
        } catch (Exception e) {
            log.warn("token encrypt error");
        }
        return token;
    }

    public static String genToken() {
        //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");

        return token;
    }

    public String createTokenFor2FA(Integer userId) {
        String token = genToken();
        redisUtil.setExpire(RedisKeyConstant.TWO_FACTOR_AUTH_REQ_TOKEN, token, 300, userId);
        redisUtil.setExpire(RedisKeyConstant.TWO_FACTOR_AUTH_REQ_LOGIN, userId, 300, token);
        try {
            token = DESEncryptUtils.encrypt(token, TWO_FACTOR_AUTH_LOGIN_KEY);
        } catch (Exception e) {
            log.warn("2FA 登入臨時token encrypt error");
        }
        return token;
    }

    public String getDESToken(String token) {
        try {
            token = DESEncryptUtils.encrypt(token, LOGIN_KEY);
        } catch (Exception e) {
            log.warn("token encrypt error");
        }
        return token;
    }

    public String getUserToken(Integer userId) {
        return redisUtil.get(RedisKeyConstant.REQ_TOKEN, userId);
    }

    public Integer getUserIdByToken(String token) {
        String deToken = DESEncryptUtils.decrypt(token, LOGIN_KEY);

        Integer userId = redisUtil.get(RedisKeyConstant.REQ_LOGIN, deToken);

        /**
         * fix bug at 2019.11.28 11:37
         * userId cant not find use null
         */
        return userId != null ? userId : null;
    }

    public Integer getUserIdBy2faLoginToken(String token) {
        String deToken = DESEncryptUtils.decrypt(token, TWO_FACTOR_AUTH_LOGIN_KEY);

        return redisUtil.get(RedisKeyConstant.TWO_FACTOR_AUTH_REQ_LOGIN, deToken);
    }

    public Integer getUserIdByTokenForActivity(String token) {
        if (null == token) {
            token = "";
        }
        String deToken = DESEncryptUtils.decrypt(token, LOGIN_KEY);

        Integer userId = redisUtil.get(RedisKeyConstant.REQ_LOGIN, deToken);

        /**
         * fix bug at 2019.11.28 11:37
         * userId cant not find use null
         */
        return userId != null ? userId : null;
    }
}
