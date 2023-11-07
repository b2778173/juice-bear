package com.juice.common.constant;

public class RedisKeyConstant {

    /**
     * 应用级别前缀
     */
    public static String REQ_TOKEN = "token::TOKEN_%s";
    /**
     * 登录用户ID前缀
     */
    public static String REQ_LOGIN = "login_uid::LOGIN_%s";
    /**
     *  2FA Key
     */
    public static String TWO_FACTOR_AUTH_REQ_TOKEN = "TWO_FACTOR_AUTH::TOKEN_%s";
    public static String TWO_FACTOR_AUTH_REQ_LOGIN = "TWO_FACTOR_AUTH::LONGIN_%s";


}
