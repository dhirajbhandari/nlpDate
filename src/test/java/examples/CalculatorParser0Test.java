package examples;

import examples.CalculatorParser0;
import org.eggs.Logger;
import org.junit.Before;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.parboiled.support.ParseTreeUtils.printNodeTree;

public class CalculatorParser0Test {

    private static Logger LOG = Logger.getLogger(CalculatorParser0Test.class);
    
    private CalculatorParser0 parser;

    @Before
    public void setUp() throws Exception {
        parser = Parboiled.createParser(CalculatorParser0.class);
    }

    @Test
    public void testExpression() throws Exception {
        String input = "3 + (75/5) * 5";
        ParsingResult<?> result = new RecoveringParseRunner(
                parser.Expression()).run(input);
        LOG.debug(input + " = " + result.parseTreeRoot.getValue() + '\n');
        LOG.debug(printNodeTree(result));

        assertThat(result, notNullValue());
    }
}