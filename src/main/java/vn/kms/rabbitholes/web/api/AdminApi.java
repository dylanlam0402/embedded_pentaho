/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.kms.rabbitholes.web.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.kms.rabbitholes.web.controller.BaseController;
import vn.kms.rabbitholes.web.util.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@RestController
@RequestMapping("/api/admin")
public class AdminApi extends BaseController {

    @GetMapping("/exec")
    public String executeCommand(@RequestParam String command, HttpServletResponse response) throws IOException {
        if (!SecurityContextHolder.getCurrentUser().isAdmin()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return executeCommand(command);
    }

    private static String executeCommand(String command) throws java.io.IOException {
        Process process = Runtime.getRuntime().exec(command);

        InputStream is = process.getInputStream();
        Scanner scanner = new Scanner(is).useDelimiter("\\A");

        String val = "";
        if (scanner.hasNext()) {
            val = scanner.next();
        }

        return val;
    }

}
