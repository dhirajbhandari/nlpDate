package org.eggs;

import org.junit.Test;

public class DayMonthYearTest extends DateParserTestCase {

    @Test
    public void testFormalDate() throws Exception {
        validateDateExpression("26/05/2009", date(2009, 5, 20));
        validateDateExpression("16/05/2009", date(2009, 5, 16));
        validateDateExpression("16-05-2009", date(2009, 5, 16));
    }

    @Test
    public void testLooseFormalDate() throws Exception {
        validateDateExpression("6/5/09", date(2009, 5, 6));
        validateDateExpression("6-5-09", date(2009, 5, 6));
    }
}