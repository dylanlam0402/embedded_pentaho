/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.kms.rabbitholes.web.controller;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.kms.rabbitholes.domain.UploadFile;
import vn.kms.rabbitholes.domain.User;
import vn.kms.rabbitholes.repository.UploadFileRepository;
import vn.kms.rabbitholes.service.FileService;
import vn.kms.rabbitholes.web.util.Constant;
import vn.kms.rabbitholes.web.util.SecurityContextHolder;
import vn.kms.rabbitholes.web.util.TokenSupporter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;


@Controller
@RequestMapping("/files")
public class FileController extends BaseController {
    @Autowired
    private FileService fileService;

    @Autowired
    private UploadFileRepository uploadFileRepo;

    @GetMapping("/upload")
    public String uploadForm() {
        return "upload";
    }

    @PostMapping
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String comment, Model model) {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setFileName(file.getOriginalFilename());
        uploadFile.setFileSize(file.getSize());
        uploadFile.setFileType(file.getContentType());
        uploadFile.setUploadComment(StringEscapeUtils.escapeHtml(comment));
        uploadFile.setUploader(SecurityContextHolder.getCurrentUser());
        String result = "";
        try {
            result = fileService.saveFile(uploadFile, file.getInputStream());

        } catch (IOException e) {
            StringWriter writer = new StringWriter().append(result);
            model.addAttribute("error", writer.toString());
            return "upload";
        }
        return "redirect:/";
    }

    @GetMapping("/{uploaderId}/{fileName:.+}")
    public void downloadFile(@PathVariable long uploaderId, @PathVariable String fileName,
                             HttpServletResponse response) throws IOException {

        User uploader = new User(uploaderId);
        UploadFile uploadFile = uploadFileRepo.findByUploaderAndFileName(uploader, fileName);
        if (uploadFile == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        }
        // check user cannot download other user file
        if (!getCurrentUser().getId().equals(uploadFile.getUploader().getId())) {
            response.sendRedirect("/");
        }
        File file = fileService.getFile(uploadFile);
        try (FileInputStream inputStream = new FileInputStream(file)) {
            response.setContentType(uploadFile.getFileType());
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    }

    @GetMapping("/delete")
    public String deleteFile(@Param("") long id, HttpServletResponse response, HttpServletRequest request) {
        String tokenForm = request.getParameter(Constant.CSRF);
        String tokenCookie = TokenSupporter.getTokenFromCookie(request, Constant.CSRF);
        if (tokenForm != null && tokenCookie != null && tokenForm.equals(tokenCookie)) {
            boolean success = fileService.deleteFile(id);
            if (!success) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }
            return "redirect:/";
        }
        return "redirect:/";
    }
}
