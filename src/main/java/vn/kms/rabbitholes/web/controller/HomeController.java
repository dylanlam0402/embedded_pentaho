/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.kms.rabbitholes.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.kms.rabbitholes.service.FileService;

import javax.servlet.http.HttpServletResponse;

import static vn.kms.rabbitholes.domain.User.Role.ADMIN;
import static vn.kms.rabbitholes.domain.User.Role.USER;

@Controller
public class HomeController extends BaseController {
    @Autowired
    private FileService fileService;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String keyword, Model model, HttpServletResponse response) {
        if (!allowedRoles(ADMIN, USER)) {
            return "redirect:signin";
        }

        model.addAttribute("files", fileService.searchFiles(getCurrentUser().getId(),getCurrentUser().getRole(), keyword));
        addToken(model, response);
        return "index";
    }
}
