package examples;

import examples.CalcNode;
import examples.CalculatorParser2;
import org.junit.Before;
import org.junit.Test;
import org.parboiled.Node;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.parboiled.support.ParseTreeUtils.printNodeTree;

public class CalculatorParser2Test {

    private CalculatorParser2 parser;

    @Before
    public void setUp() throws Exception {
        parser = Parboiled.createParser(CalculatorParser2.class);
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
        RecoveringParseRunner runner = new RecoveringParseRunner(parser.Expression());
        ParsingResult<CalcNode> result = runner.run(input);
        System.out.println(input + " = " + result.parseTreeRoot.getValue() + '\n');
        System.out.println(printNodeTree(result));

        assertThat(result, notNullValue());

        Node<CalcNode> parseTreeRoot = result.parseTreeRoot;
        System.out.println("node class: " + parseTreeRoot.getClass());
        CalcNode value = parseTreeRoot.getValue();
        assertThat(value.getValue(), equalTo(resultInt));
    }
}