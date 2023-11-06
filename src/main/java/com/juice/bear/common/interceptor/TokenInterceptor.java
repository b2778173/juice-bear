package com.juice.bear.common.interceptor;

/**
 * Created by ken on 2018/9/4.
 */

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {


    @Value("${spring.profiles.active}")
    private String env;

    @Value("${global.forehead.pass.env}")
    private List<String> passEnv;

    /**
     * pre handle
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return false;
    }

    /**
     * validate token
     *
     * @return boolean
     */
    private boolean validateToken(String uid, String token) {
        return false;
    }

    /**
     * 判断是否在其它设备登录
     *
     * @param uid
     * @param token
     * @return
     */
    private boolean validateDeviceToken(String uid, String token) {
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
//        MDC.remove(MdcConstant.USER_NAME);
    }
}
