/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.kms.rabbitholes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.kms.rabbitholes.domain.User;
import vn.kms.rabbitholes.repository.UserRepository;
import vn.kms.rabbitholes.web.util.EncryptionUtil;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    public User signin(String email, String password) {
        User user = userRepo.findByEmail(email);

        // invalid email
        if (user == null) {
            return null;
        }

        // invalid password
        if (!user.getPassword().equals(EncryptionUtil.toMD5(password))) {
            return null;
        }

        return user;
    }

    public void registerUser(String email, String password, String firstName, String lassName) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(EncryptionUtil.toMD5(password));
        user.setFirstName(firstName);
        user.setLastName(lassName);
        user.setRole(User.Role.USER);
        userRepo.save(user);
    }
}
