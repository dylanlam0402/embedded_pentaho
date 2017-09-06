/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.kms.rabbitholes.repository;

import vn.kms.rabbitholes.domain.UploadFile;
import vn.kms.rabbitholes.domain.User;

import java.util.List;

public interface CustomUploadFileRepository {

    List<UploadFile> findByKeyword(long uploaderId, User.Role role, String keyword);
}
