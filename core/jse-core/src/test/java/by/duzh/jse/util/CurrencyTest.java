package by.duzh.jse.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.Locale;

public class CurrencyTest {
    private Currency currency;

    @BeforeEach
    public void init() {
        currency = Currency.getInstance(Locale.US);
    }

    @Test
    public void testSchedule() {
        Assertions.assertTrue(Currency.getAvailableCurrencies()
                .stream()
                .anyMatch(c -> "USD".equals(c.getCurrencyCode())));
    }

    @Test
    public void testGetCurrencyCode() {
        Assertions.assertEquals("USD", currency.getCurrencyCode());
    }

    @Test
    public void testGetDefaultFractionDigits() {
        Assertions.assertEquals(2, currency.getDefaultFractionDigits());
    }

    @Test
    public void testGetDisplayName() {
        Assertions.assertEquals("US Dollar", currency.getDisplayName());
    }

    @Test
    public void testGetNumericCode() {
        Assertions.assertEquals(840, currency.getNumericCode());
    }

    @Test
    public void testSymbol() {
        Assertions.assertEquals("$", currency.getSymbol());
    }

    @Test
    public void testToString() {
        Assertions.assertEquals(currency.getCurrencyCode(), currency.toString());
    }
}
