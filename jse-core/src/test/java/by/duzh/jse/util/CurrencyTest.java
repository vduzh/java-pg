package by.duzh.jse.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Currency;
import java.util.Locale;

public class CurrencyTest {
    private Currency currency;

    @Before
    public void init() {
        currency = Currency.getInstance(Locale.US);
    }

    @Test
    public void testSchedule() {
        Assert.assertTrue(Currency.getAvailableCurrencies()
                .stream()
                .anyMatch(c -> "USD".equals(c.getCurrencyCode())));
    }

    @Test
    public void testGetCurrencyCode() {
        Assert.assertEquals("USD", currency.getCurrencyCode());
    }

    @Test
    public void testGetDefaultFractionDigits() {
        Assert.assertEquals(2, currency.getDefaultFractionDigits());
    }

    @Test
    public void testGetDisplayName() {
        Assert.assertEquals("US Dollar", currency.getDisplayName());
    }

    @Test
    public void testGetNumericCode() {
        Assert.assertEquals(840, currency.getNumericCode());
    }

    @Test
    public void testSymbol() {
        Assert.assertEquals("$", currency.getSymbol());
    }

    @Test
    public void testToString() {
        Assert.assertEquals(currency.getCurrencyCode(), currency.toString());
    }
}
