package vn.kms.rabbitholes.web.util;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kietlam
 */
public class SecurityPolicy {

    private static Map<String, String> securityPolicy = new HashMap<String, String>() {{
        put("Strict-Transport-Security", "max-age=31536000 ; includeSubDomains");
        put("X-Content-Type-Options", "nosniff");
        put("X-Frame-Options", "DENY");
        put("X-XSS-Protection", "1; mode=block");
        put("Content-Security-Policy", "default-src 'self'");
    }};

    private SecurityPolicy() {
    }

    public static void addPolicy(HttpServletResponse response) {
        securityPolicy.forEach((k, v) -> response.setHeader(k, v));
    }
}
