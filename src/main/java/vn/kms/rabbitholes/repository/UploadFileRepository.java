/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.kms.rabbitholes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.kms.rabbitholes.domain.UploadFile;
import vn.kms.rabbitholes.domain.User;

@Repository
public interface UploadFileRepository extends CrudRepository<UploadFile, Long>, CustomUploadFileRepository {
    UploadFile findByUploaderAndFileName(User uploader, String fileName);
}
