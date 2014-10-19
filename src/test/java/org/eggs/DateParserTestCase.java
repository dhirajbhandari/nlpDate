package org.eggs;

import org.eggs.nodes.Node;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.parboiled.support.ParseTreeUtils.printNodeTree;

public class DateParserTestCase {

    private static DateParser parser;
    private static Logger LOG;

    private ParsingContext parsingContext;

    @BeforeClass
    public static void buildParser() throws Exception {
        parser = Parboiled.createParser(DateParser.class);
        LOG = Logger.getLogger(DateParserTestCase.class.getName());
    }

    @Before
    public void setUp() throws Exception {
        parsingContext = new ParsingContext(date(2014,10,15));
        parser.setParsingContext(parsingContext);
    }

    protected void validateDateExpression(String input, DateTime expected) throws Exception {
        RecoveringParseRunner runner = new RecoveringParseRunner(parser.InputLine());
        ParsingResult<Node> result = runner.run(input);
        LOG.debug(input + " = " + result.parseTreeRoot.getValue() + '\n');
        LOG.debug(printNodeTree(result));

        assertThat(result, notNullValue());

        org.parboiled.Node<Node> parseTreeRoot = result.parseTreeRoot;
        LOG.debug("node class: " + parseTreeRoot.getClass());
        Node value = parseTreeRoot.getValue();
        assertThat(value.getDate(), equalTo(expected));
    }

    protected DateTime date(int year, int month, int day) {
        return new DateTime(year, month, day, 0, 0);
    }
}