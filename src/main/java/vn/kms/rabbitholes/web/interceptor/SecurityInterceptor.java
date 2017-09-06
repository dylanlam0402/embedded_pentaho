/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.kms.rabbitholes.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import vn.kms.rabbitholes.domain.User;
import vn.kms.rabbitholes.service.TokenAuthenticationService;
import vn.kms.rabbitholes.web.util.Constant;
import vn.kms.rabbitholes.web.util.SecurityContextHolder;
import vn.kms.rabbitholes.web.util.SecurityPolicy;
import vn.kms.rabbitholes.web.util.TokenSupporter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user = (User) TokenAuthenticationService.getAuthentication(
            TokenSupporter.getTokenFromCookie(request, Constant.AUTHORIZATION));

        if (user == null) {
            user = User.ANONYMOUS_USER;
        }
        setSecurityHeader(response);
        SecurityContextHolder.setCurrentUser(user);
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);


    }

    /**
     * add Content Policy to prevent XSS Attack
     *
     * @param response
     */
    private void setSecurityHeader(HttpServletResponse response) {
        SecurityPolicy.addPolicy(response);
    }

}
