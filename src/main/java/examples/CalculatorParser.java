package examples;

import org.eggs.Logger;
import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.common.StringUtils;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;
import org.parboiled.support.ToStringFormatter;
import org.parboiled.trees.GraphNode;

import java.util.Scanner;


import static org.parboiled.errors.ErrorUtils.printParseErrors;
import static org.parboiled.support.ParseTreeUtils.printNodeTree;
import static org.parboiled.trees.GraphUtils.printTree;

public abstract class CalculatorParser<V> extends BaseParser<V> {

    private static Logger LOG = Logger.getLogger(CalculatorParser.class);
    
    public abstract Rule InputLine();

    @SuppressWarnings({"unchecked"})
    public static <V, P extends CalculatorParser<V>> void main(Class<P> parserClass) {
        CalculatorParser<V> parser = Parboiled.createParser(parserClass);

        while (true) {
            LOG.debug("Enter a calculators expression (single RETURN to exit)!\n");
            String input = new Scanner(System.in).nextLine();
            if (StringUtils.isEmpty(input)) break;

            ParsingResult<?> result = new RecoveringParseRunner(parser.InputLine()).run(input);

            if (result.hasErrors()) {
                LOG.debug("\nParse Errors:\n" + printParseErrors(result));
            }

            Object value = result.parseTreeRoot.getValue();
            if (value != null) {
                String str = value.toString();
                int ix = str.indexOf('|');
                if (ix >= 0) str = str.substring(ix + 2); // extract value part of AST node toString()
                LOG.debug(input + " = " + str + '\n');
            }
            if (value instanceof GraphNode) {
                LOG.debug("\nAbstract Syntax Tree:\n" +
                        printTree((GraphNode) value, new ToStringFormatter(null)) + '\n');
            } else {
                LOG.debug("\nParse Tree:\n" + printNodeTree(result) + '\n');
            }
        }
    }


}
