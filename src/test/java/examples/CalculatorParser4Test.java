package examples;

import org.eggs.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.parboiled.Node;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.parboiled.support.ParseTreeUtils.printNodeTree;

public class CalculatorParser4Test {

    private static Logger LOG = Logger.getLogger(CalculatorParser4Test.class);

    private CalculatorParser4 parser;

    @Before
    public void setUp() throws Exception {
        parser = Parboiled.createParser(CalculatorParser4.class);
    }

    @Test
    public void testSimpleExpression() throws Exception {
        checkExpression("3", 3.0);
    }

    @Test
    public void testDecimalNumbers() throws Exception {
        checkExpression("3", 3.0);
        checkExpression("3.5", 3.5);
        checkExpression("0.5", 0.5);
        checkExpression(".5", 0.5);
        checkExpression("-.5", -0.5);
    }

    @Test
    public void testNegativeExpression() throws Exception {
        checkExpression("-3", -3.0);
    }

    @Test
    public void testTermExpression() throws Exception {
        checkExpression("3+5", 8.0);
        checkExpression("3-5", -2.0);
    }

    @Test
    public void testFactorExpression() throws Exception {
        checkExpression("3*5", 15.0);
        checkExpression("3*5*5", 75.0);
        checkExpression("3+5*5", 28.0);
        checkExpression("3+5*5+2", 30.0);
        checkExpression("150/5/5", 6.0);
    }

    @Test
    public void testParenExpression() throws Exception {
        checkExpression("2*3+5", 11.0);
        checkExpression("2*(3+5)", 16.0);
        checkExpression("30/5+5", 11.0);
        checkExpression("30/(5+5)", 3.0);
    }

    @Test
    public void testPowerExpression() throws Exception {
        checkExpression("2^1", 2.0);
        checkExpression("2^3", 8.0);
        checkExpression("2^8", 256.0);
        checkExpression("3^3", 27.0);
        checkExpression("10^0", 1.0);
    }

    @Test
    public void testSquareRootExpression() throws Exception {
        checkExpression("SQRT(4)", 2.0);
        checkExpression("SQRT(16)", 4.0);
        checkExpression("SQRT(9)", 3.0);
    }

    @Test
    public void testExpressionWithSpaces() throws Exception {
        checkExpression("3 + 5", 8.0);
        checkExpression(" 3 + 5 ", 8.0);
        checkExpression("3 + 5 ", 8.0);
        checkExpression("3 ^ 2 ", 9.0);
        checkExpression("SQRT (3 + 6) ", 3.0);
        checkExpression("SQRT(3 + 6)+5", 8.0);
        checkExpression("SQRT (3^ 2) + -5 * -3", 18.0);
    }

    @Test
    @Ignore
    public void testExpressionStartingWithSpaces() throws Exception {
        //starting space fails
        checkExpression(" SQRT (3^ 2) + -5 * -3", 18.0);
    }

    protected void checkExpression(String input, Double expected) throws Exception {
        RecoveringParseRunner runner = new RecoveringParseRunner(parser.Expression());
        ParsingResult<CalcNode> result = runner.run(input);
        LOG.debug(input + " = " + result.parseTreeRoot.getValue() + '\n');
        LOG.debug(printNodeTree(result));

        assertThat(result, notNullValue());

        Node<CalcNode> parseTreeRoot = result.parseTreeRoot;
        LOG.debug("node class: " + parseTreeRoot.getClass());
        CalcNode value = parseTreeRoot.getValue();
        assertThat(value.doubleValue(), equalTo(expected));
    }
}