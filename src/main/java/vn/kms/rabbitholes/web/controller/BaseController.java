/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.kms.rabbitholes.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import vn.kms.rabbitholes.domain.User;
import vn.kms.rabbitholes.web.util.Constant;
import vn.kms.rabbitholes.web.util.CookieGenerator;
import vn.kms.rabbitholes.web.util.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;

public abstract class BaseController {
    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        return SecurityContextHolder.getCurrentUser();
    }


    public String generateTOken() {
        SecureRandom random = new SecureRandom();
        return "" + random.nextLong();
    }

    public void addToken(Model model, HttpServletResponse response) {
        String token = generateTOken();
        model.addAttribute(Constant.CSRF, token);
        response.addCookie(CookieGenerator.initCookie(Constant
            .CSRF, token));
    }

    protected boolean allowedRoles(User.Role... roles) {
        for (User.Role role : roles) {
            if (getCurrentUser().getRole() == role) {
                return true;
            }
        }

        return false;
    }
}
