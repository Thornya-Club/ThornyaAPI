package test.thornyaplugin.thornyaplugin.utils;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import org.junit.Assert;

import java.math.BigDecimal;

import static thornyaplugin.thornyaplugin.utils.Utils.formatarMoney;

/**
 * Utils Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>05/24/2021</pre>
 */
public class UtilsTest extends TestCase {

    public UtilsTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testFormatarMoneyFloat() throws Exception {
        final String formatarMoney = formatarMoney(10F);
        Assert.assertEquals(formatarMoney, "§cT10.00");
    }

    public void testFormatarMoneyBigDecimal() throws Exception {
        final String formatarMoney = formatarMoney(new BigDecimal(10));
        Assert.assertEquals(formatarMoney, "§cT10.00");
    }

    public static Test suite() {
        return new TestSuite(UtilsTest.class);
    }
}
