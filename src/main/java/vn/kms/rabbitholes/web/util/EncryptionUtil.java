package vn.kms.rabbitholes.web.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author kietlam
 */
public class EncryptionUtil {
    private static final Logger LOG = LoggerFactory.getLogger(EncryptionUtil.class.getCanonicalName());

    private EncryptionUtil() {

    }

    /**
     * @param string value
     * @return string value encrypt MD5
     */
    public static final String toMD5(String string) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(string.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                String hexStr = Integer.toHexString(0xFF & messageDigest[i]);
                while (hexStr.length() < 2) {
                    hexStr = "0" + hexStr;
                }
                stringBuilder.append(hexStr);
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            LOG.debug("cannot find menthod");
        }
        return "";

    }
}
