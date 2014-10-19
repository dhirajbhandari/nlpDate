package org.eggs;

import org.junit.Ignore;
import org.junit.Test;

public class RelativeDateTest extends DateParserTestCase {

    @Test
    public void testRelativeDate() throws Exception {
        validateDateExpression("yesterday", date(2014, 10, 15 - 1));
        validateDateExpression("last week", date(2014, 10, 15 - 7));
        validateDateExpression("last month", date(2014, 9, 15));
        validateDateExpression("last year", date(2013, 10, 15));

        validateDateExpression("tomorrow", date(2014, 10, 15 + 1));
        validateDateExpression("next week", date(2014, 10, 15 + 7));
        validateDateExpression("next month", date(2014, 10 + 1, 15));
        validateDateExpression("next year", date(2014 + 1, 10, 15));
    }

    @Test
    @Ignore
    public void testStartOfRelativeDate() throws Exception {
        validateDateExpression("start of this week", date(2014, 10, 15 - 1));
    }
}