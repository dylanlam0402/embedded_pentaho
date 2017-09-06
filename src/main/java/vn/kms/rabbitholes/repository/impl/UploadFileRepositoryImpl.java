/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.kms.rabbitholes.repository.impl;

import vn.kms.rabbitholes.domain.UploadFile;
import vn.kms.rabbitholes.domain.User;
import vn.kms.rabbitholes.repository.CustomUploadFileRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


public class UploadFileRepositoryImpl implements CustomUploadFileRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<UploadFile> findByKeyword(long uploaderId, User.Role role, String keyword) {
        String sql = "";
        if (role.equals(User.Role.ADMIN)) {
            sql = "SELECT u FROM UploadFile WHERE ( u.fileName LIKE CONCAT('%',?2,'%') OR u.uploadComment LIKE CONCAT('%',?2,'%') " +
                "OR u.fileType LIKE CONCAT('%',?2,'%') )";
        } else {
            sql = "SELECT u FROM UploadFile u WHERE u.uploader.id = ?1 " +
                "AND ( u.fileName LIKE CONCAT('%',?2,'%') OR u.uploadComment LIKE CONCAT('%',?2,'%') " +
                "OR u.fileType LIKE CONCAT('%',?2,'%') )";
        }
        Query query = em.createQuery(sql);
        query.setParameter(1, uploaderId);
        query.setParameter(2, keyword);


        return query.getResultList();
    }


}
