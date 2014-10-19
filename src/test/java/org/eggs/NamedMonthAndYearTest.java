package org.eggs;

import org.junit.Test;

public class NamedMonthAndYearTest extends DateParserTestCase {

    @Test
    public void testMonthNameYear() throws Exception {
        validateDateExpression("mar 2009", date(2009, 3, 1));
        validateDateExpression("december 2020", date(2020, 12, 1));
    }
}