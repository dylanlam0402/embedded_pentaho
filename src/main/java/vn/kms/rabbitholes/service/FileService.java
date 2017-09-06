/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.kms.rabbitholes.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.kms.rabbitholes.domain.UploadFile;
import vn.kms.rabbitholes.domain.User;
import vn.kms.rabbitholes.repository.UploadFileRepository;
import vn.kms.rabbitholes.web.util.Constant;
import vn.kms.rabbitholes.web.util.FileExtension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class FileService {
    @Value("${rabbitholes.upload-location}")
    private String uploadLocation;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    public String saveFile(UploadFile uploadFile, InputStream inputStream) throws IOException {
        if (FileExtension.checkFileExtension(uploadFile.getFileName())) {
            uploadFileRepository.save(uploadFile);

            File file = getFile(uploadFile);
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                IOUtils.copy(inputStream, fileOutputStream);
            }
            return Constant.SAVE_FILE_SUCCESS;
        }
        return Constant.SAVE_FILE_ERROR;
    }

    public List<UploadFile> searchFiles(Long userId, User.Role role, String keyword) {
        return uploadFileRepository.findByKeyword(userId, role, keyword);
    }

    public UploadFile getUploadFileById(Long id) {
        return uploadFileRepository.findOne(id);
    }

    public boolean deleteFile(long uploadFileId) {
        UploadFile uploadFile = uploadFileRepository.findOne(uploadFileId);
        if (uploadFile == null) {
            return false;
        }

        uploadFileRepository.delete(uploadFile);

        File file = getFile(uploadFile);
        if (file.exists()) {
            file.delete();
        }

        return true;
    }

    public File getFile(UploadFile uploadFile) {
        String uploaderId = String.valueOf(uploadFile.getUploader().getId());
        File uploadDir = new File(uploadLocation, uploaderId);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        return new File(uploadDir, uploadFile.getFileName());
    }

}
