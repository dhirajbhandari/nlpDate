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
    public void example_15_dec_2009() throws Exception {
        validateDateExpression("15 dec, 2009", date(2009, 12, 15));
        validateDateExpression("15 dec 2009", date(2009, 12, 15));
    }

    @Test
    public void example_15th_dec_2009() throws Exception {
        validateDateExpression("15th dec, 2009", date(2009, 12, 15));
        validateDateExpression("15th dec 2009", date(2009, 12, 15));
    }

    @Test
    public void example_dec_15_2011() throws Exception {
        validateDateExpression("dec 15, 2011", date(2011, 12, 15));
        validateDateExpression("dec 15 2011", date(2011, 12, 15));
    }

    @Test
    public void example_dec_15th_2011() throws Exception {
        validateDateExpression("dec 15th, 2011", date(2011, 12, 15));
        validateDateExpression("dec 15th 2011", date(2011, 12, 15));
    }

    @Test
    public void example_15_december_2020() throws Exception {
        validateDateExpression("15 december, 2020", date(2020, 12, 15));
        validateDateExpression("15 december 2020", date(2020, 12, 15));
    }

    @Test
    public void example_15th_december_2020() throws Exception {
        validateDateExpression("15th december, 2020", date(2020, 12, 15));
        validateDateExpression("15th december 2020", date(2020, 12, 15));
    }

    @Test
    public void example_december_15_2011() throws Exception {
        validateDateExpression("december 15, 2011", date(2011, 12, 15));
        validateDateExpression("december 15 2011", date(2011, 12, 15));
    }

    @Test
    public void example_december_15th_2011() throws Exception {
        validateDateExpression("december 15th, 2011", date(2011, 12, 15));
        validateDateExpression("december 15th 2011", date(2011, 12, 15));
    }

    @Test
    public void example_1st_jan_2011() throws Exception {
        validateDateExpression("1st jan 2011", date(2011, 1, 1));
        validateDateExpression("21st jan 2011", date(2011, 1, 21));
        validateDateExpression("31st jan 2011", date(2011, 1, 31));

        validateDateExpression("1st jan, 2011", date(2011, 1, 1));
        validateDateExpression("21st jan, 2011", date(2011, 1, 21));
        validateDateExpression("31st jan, 2011", date(2011, 1, 31));
    }


    @Test
    public void example_2nd_feb_2011() throws Exception {
        validateDateExpression("2nd feb 2011", date(2011, 2, 2));
        validateDateExpression("22nd feb 2011", date(2011, 2, 22));

        validateDateExpression("2nd feb, 2011", date(2011, 2, 2));
        validateDateExpression("22nd feb, 2011", date(2011, 2, 22));
    }
}