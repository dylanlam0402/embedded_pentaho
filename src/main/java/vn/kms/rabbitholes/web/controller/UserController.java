/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.kms.rabbitholes.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import vn.kms.rabbitholes.domain.User;
import vn.kms.rabbitholes.service.TokenAuthenticationService;
import vn.kms.rabbitholes.service.UserService;
import vn.kms.rabbitholes.web.form.LoginForm;
import vn.kms.rabbitholes.web.form.RegistrationForm;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerForm(RegistrationForm registrationForm) {
        return "register";
    }

    @PostMapping("/register-submit")
    public String registerSubmit(@Valid RegistrationForm registrationForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.registerUser(
            registrationForm.getEmail(),
            registrationForm.getPassword(),
            registrationForm.getFirstName(),
            registrationForm.getLastName());

        return "redirect:/signin";
    }

    @GetMapping("/signin")
    public String signinForm(LoginForm loginForm, Model model, HttpServletResponse response) {
        addToken(model, response);
        return "signin";
    }

    @PostMapping("/signin-submit")
    public Object signinSubmit(@Valid LoginForm loginForm,
                               BindingResult bindingResult,
                               Model model,
                               HttpServletResponse response) throws JsonProcessingException {

        if (bindingResult.hasErrors()) {
            return "signin";
        }

        User user = userService.signin(loginForm.getEmail(), loginForm.getPassword());
        if (user == null) {
            model.addAttribute("authenFailed", true);
            return "signin";
        }
        // When user sign in parse User to JSON String
        ObjectMapper objectMapper = new ObjectMapper();
        TokenAuthenticationService.addAuthentication(response, objectMapper.writeValueAsString(user));
        return "index";
    }

    @PostMapping("/signout")
    public String signout(HttpServletRequest req, HttpServletResponse resp) {
        eraseCookie(req, resp);
        return "redirect:/";
    }

    /**
     * Clear all cookie when logout
     *
     * @param req
     * @param resp
     */
    private void eraseCookie(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
    }
}
