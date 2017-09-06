/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.kms.rabbitholes.web.util;

import vn.kms.rabbitholes.domain.User;

public final class SecurityContextHolder {
    private static final ThreadLocal<User> currentUserHolder = new ThreadLocal<>();

    public static User getCurrentUser() {
        return currentUserHolder.get();
    }

    public static void setCurrentUser(User user) {
        currentUserHolder.set(user);
    }
}
