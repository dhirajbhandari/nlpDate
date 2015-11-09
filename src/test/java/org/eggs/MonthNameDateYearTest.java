package org.eggs;

import org.junit.Test;

public class MonthNameDateYearTest extends DateParserTestCase {

    @Test
    public void testMonthNameDateYearWithoutCommas() throws Exception {
        //NamedMonthAndYearTest
        validateDateExpression("10 feb 2009", date(2009, 2, 10));
        validateDateExpression("15 december 2020", date(2020, 12, 15));
        validateDateExpression("december 15 2011", date(2011, 12, 15));
        validateDateExpression("dec 15 2011", date(2011, 12, 15));
    }

    @Test
    public void example_10_feb_2009() throws Exception {
        validateDateExpression("10 feb, 2009", date(2009, 2, 10));
        validateDateExpression("10 feb 2009", date(2009, 2, 10));
    }

    @Test
    public void example_15_december_2020() throws Exception {
        validateDateExpression("15 december, 2020", date(2020, 12, 15));
        validateDateExpression("15 december 2020", date(2020, 12, 15));
    }

    @Test
    public void example_december_15_2011() throws Exception {
        validateDateExpression("december 15, 2011", date(2011, 12, 15));
        validateDateExpression("december 15 2011", date(2011, 12, 15));
    }

    @Test
    public void example_dec_15_2011() throws Exception {
        validateDateExpression("dec 15, 2011", date(2011, 12, 15));
        validateDateExpression("dec 15 2011", date(2011, 12, 15));
    }
}