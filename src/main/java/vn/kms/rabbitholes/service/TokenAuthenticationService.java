package vn.kms.rabbitholes.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import vn.kms.rabbitholes.domain.User;
import vn.kms.rabbitholes.web.util.Constant;
import vn.kms.rabbitholes.web.util.CookieGenerator;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author kietlam
 */
public class TokenAuthenticationService {
    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "ThisIsASecret";


    /**
     * add token generate from user information to cookie.
     *
     * @param res
     * @param jsonString
     */
    public static void addAuthentication(HttpServletResponse res, String jsonString) {
        String token = Jwts.builder()
            .setSubject(jsonString)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();
        res.addCookie(CookieGenerator.initCookie(Constant.AUTHORIZATION, token));
    }

    /**
     * @param token
     * @return parse User Object from token
     */
    public static Object getAuthentication(String token) {
        if (token != null) {
            // parse the token.
            String userString = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
            ObjectMapper objectMapper = new ObjectMapper();
            User user = null;
            try {
                user = objectMapper.readValue(userString, User.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return user;
        }
        return null;
    }
}
