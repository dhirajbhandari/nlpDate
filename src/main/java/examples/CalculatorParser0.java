package examples;

import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.common.StringUtils;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import java.util.Scanner;



import static org.parboiled.errors.ErrorUtils.printParseErrors;
import static org.parboiled.support.ParseTreeUtils.printNodeTree;
import static org.parboiled.trees.GraphUtils.printTree;


@BuildParseTree
public class CalculatorParser0 extends CalculatorParser<Integer> {

    public Rule InputLine() {
        return Sequence(Expression(), EOI);
    }

    Rule Expression() {
        return Sequence(
                Term(),
                ZeroOrMore(AnyOf("+-"), Term())
        );
    }

    Rule Term() {
        return Sequence(
                Factor(),
                ZeroOrMore(AnyOf("*/"), Factor())
        );
    }

    Rule Factor() {
        return FirstOf(
                Number(),
                Sequence('(', Expression(), ')')
        );
    }

    Rule Number() {
        return OneOrMore(CharRange('0', '9'));
    }

    public static void mainOld(String[] args) {
        main(CalculatorParser0.class);
    }


    public static void main(String[] args) {
        CalculatorParser0 parser = Parboiled.createParser(CalculatorParser0.class);
        while (true) {
            System.out.print("Enter an expression (hh:mm(:ss)?, hh(mm(ss)?)? or h(mm)?, single RETURN to exit)!\n");
            String input = new Scanner(System.in).nextLine();
            if (StringUtils.isEmpty(input)) break;

            ParsingResult<?> result = new RecoveringParseRunner(parser.Expression()).run(input);

            System.out.println(input + " = " + result.parseTreeRoot.getValue() + '\n');
            System.out.println(printNodeTree(result) + '\n');

            if (!result.matched) {
                System.out.println(StringUtils.join(result.parseErrors, "---\n"));
            }
        }

    }
}