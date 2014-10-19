package examples;

import examples.CalculatorParser1;
import org.eggs.Logger;
import org.junit.Before;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.parboiled.support.ParseTreeUtils.printNodeTree;

public class CalculatorParser1Test {
    
    private static Logger LOG = Logger.getLogger(CalculatorParser1Test.class);

    private CalculatorParser1 parser;

    @Before
    public void setUp() throws Exception {
        parser = Parboiled.createParser(CalculatorParser1.class);
    }

    @Test
    public void testSimpleExpression() throws Exception {
        checkExpression("3", 3);
    }

    @Test
    public void testTermExpression() throws Exception {
        checkExpression("3+5", 8);
//        checkExpression("3 + 5", 8);
    }

    @Test
    public void testFactorExpression() throws Exception {
        checkExpression("3*5", 15);
        checkExpression("3*5*5", 75);
    }

    @Test
    public void testParenExpression() throws Exception {
        checkExpression("(3+5)", 8);
        checkExpression("(3*5)", 15);
        checkExpression("5*(3+5)", 40);
    }

    protected void checkExpression(String input, Integer resultInt) throws Exception {
        ParsingResult<?> result = new RecoveringParseRunner(
                parser.Expression()).run(input);
        LOG.debug(input + " = " + result.parseTreeRoot.getValue() + '\n');
        LOG.debug(printNodeTree(result));

        assertThat(result, notNullValue());
        assertThat((Integer) result.resultValue, equalTo(resultInt));
    }
}