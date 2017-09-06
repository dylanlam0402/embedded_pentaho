package vn.kms.rabbitholes.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.kms.rabbitholes.domain.DTO.UploadFileDTO;
import vn.kms.rabbitholes.domain.UploadFile;
import vn.kms.rabbitholes.service.FileService;
import vn.kms.rabbitholes.web.controller.BaseController;
import vn.kms.rabbitholes.web.util.Constant;
import vn.kms.rabbitholes.web.util.TokenSupporter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kietlam
 */
@RestController
@RequestMapping("/api/file")
public class FileApi extends BaseController {
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/delete")
    boolean delete(@RequestBody UploadFileDTO uploadFileDTO, HttpServletRequest request) {
        String token = TokenSupporter.getTokenFromCookie(request, Constant.CSRF);
        UploadFile uploadFile = fileService.getUploadFileById(uploadFileDTO.getId());
        boolean result = false;
        // check user delete right file
        if (uploadFile.getUploader().getEmail().equals(getCurrentUser().getEmail())) {
            if (token != null && uploadFileDTO.getToken() != null && token.equals(uploadFileDTO.getToken())) {
                result = fileService.deleteFile(uploadFileDTO.getId());
            }
        }
        return result;
    }

}
