package vn.kms.rabbitholes.web.util;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * @author kietlam
 */
public class EncryptionUtilTest {
    @Test
    public void toMD5() throws Exception {
        String actualResult = EncryptionUtil.toMD5("user@123");
        String expectedResult = "ba5ef51294fea5cb4eadea5306f3ca3b";
        MatcherAssert.assertThat("check md 5 hash user@123 ", actualResult, CoreMatchers.is(expectedResult));
    }

}
