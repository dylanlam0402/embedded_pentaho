package vn.kms.rabbitholes.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author kietlam
 */
public class TokenSupporter {


    public static String getTokenFromCookie(HttpServletRequest request, String tokenName) {
        if (request == null || request.getCookies() == null) {
            return null;
        }

        for (Cookie cookie : request.getCookies()) {
            if (tokenName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    public static String getTokenFromHeader(HttpServletRequest request, String headerName) {
        String header = request.getHeader(headerName);
        if (header != null && "".equals(header)) {
            return header;
        }
        return null;


    }
}
