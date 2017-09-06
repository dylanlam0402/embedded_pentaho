package vn.kms.rabbitholes.web.util;

import javax.servlet.http.Cookie;

/**
 * @Author kietlam
 */
public class CookieGenerator {
    private static final int MAX_AGE = 60 * 60 * 24; // 24 hours
    private static final boolean HTTP_ONLY = true;
    private static final boolean SECURE = true;

    public static Cookie initCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(HTTP_ONLY);
        cookie.setSecure(SECURE);
        cookie.setMaxAge(MAX_AGE);
        return cookie;
    }

}
