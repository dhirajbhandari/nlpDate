package org.eggs;

import org.eggs.nodes.Node;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.parboiled.support.ParseTreeUtils.printNodeTree;

public class DateParserTest {

    private DateParser parser;
    private ParsingContext parsingContext;

    @Before
    public void setUp() throws Exception {
        parsingContext = new ParsingContext(date(2014,10,15));
        parser = Parboiled.createParser(DateParser.class);
        parser.setParsingContext(parsingContext);
    }

    @Test
    public void testFormalDate() throws Exception {
        checkDateExpression("16/05/2009", date(2009, 5, 16));
        checkDateExpression("16/05/09", date(2009, 5, 16));
        checkDateExpression("16-05-2009", date(2009, 5, 16));
        checkDateExpression("16-05-09", date(2009, 5, 16));
    }

    @Test
    public void testLooseFormalDate() throws Exception {
        checkDateExpression("6/5/2009", date(2009, 5, 6));
        checkDateExpression("6/5/09", date(2009, 5, 6));
        checkDateExpression("6-5-2009", date(2009, 5, 6));
        checkDateExpression("6-5-09", date(2009, 5, 6));
    }

    @Test
    public void testRelativeDate() throws Exception {
        checkDateExpression("yesterday", date(2014, 10, 15-1));
        checkDateExpression("last week", date(2014, 10, 15-7));
        checkDateExpression("last month", date(2014, 9, 15));
        checkDateExpression("last year", date(2013, 10, 15));

        checkDateExpression("tomorrow", date(2014, 10, 15+1));
        checkDateExpression("next week", date(2014, 10, 15+7));
        checkDateExpression("next month", date(2014, 10+1, 15));
        checkDateExpression("next year", date(2014+1, 10, 15));
    }

    @Test
    @Ignore
    public void testStartOfRelativeDate() throws Exception {
        checkDateExpression("start of this week", date(2014, 10, 15-1));
    }

    protected void checkDateExpression(String input, DateTime expected) throws Exception {
        RecoveringParseRunner runner = new RecoveringParseRunner(parser.InputLine());
        ParsingResult<Node> result = runner.run(input);
        System.out.println(input + " = " + result.parseTreeRoot.getValue() + '\n');
        System.out.println(printNodeTree(result));

        assertThat(result, notNullValue());

        org.parboiled.Node<Node> parseTreeRoot = result.parseTreeRoot;
        System.out.println("node class: " + parseTreeRoot.getClass());
        Node value = parseTreeRoot.getValue();
        assertThat(value.getDate(), equalTo(expected));
    }

    protected DateTime date(int year, int month, int day) {
        return new DateTime(year, month, day, 0, 0);
    }
}