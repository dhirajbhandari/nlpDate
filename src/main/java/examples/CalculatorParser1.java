package examples;

import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.annotations.SuppressSubnodes;
import org.parboiled.common.StringUtils;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import java.util.Scanner;

import static org.parboiled.support.ParseTreeUtils.printNodeTree;


@BuildParseTree
public class CalculatorParser1 extends CalculatorParser<Integer> {

    public Rule InputLine() {
        return Sequence(Expression(), EOI);
    }

    Rule Expression() {
        return Sequence(
                Term(),
                ZeroOrMore(
                        FirstOf(
                                Sequence("+", Term(), push(pop() + pop())),
                                Sequence("-", Term(), push(pop(1) + pop()))
                        )));
    }

    Rule Term() {
        return Sequence(
                Factor(),
                ZeroOrMore(
                        FirstOf(
                                Sequence("*", Factor(), push(pop() * pop())),
                                Sequence("/", Factor(), push(pop(1) / pop()))
                        )
//                        AnyOf("*/"), Factor()
                )
        );
    }

    Rule Factor() {
        return FirstOf(
                Number(),
                Parens()
//                Sequence('(', Expression(), ')')
        );
    }

    Rule Parens() {
        return Sequence("(",
                Expression(),
                ")"
        );
    }

    Rule Number() {
        return Sequence(Digits(),
                // parse the input text matched by the preceding "Digits" rule,
                // convert it into an Integer and push it onto the value stack
                // the action uses a default string in case it is run during error recovery (resynchronization)
                push(Integer.parseInt(matchOrDefault("0"))));
    }

    @SuppressSubnodes
    Rule Digits() {
        return OneOrMore(Digit());
    }


    Rule Digit() {
        return CharRange('0', '9');
    }

    public static void main(String[] args) {
        main(CalculatorParser1.class);
    }


    public static void mainXXX(String[] args) {
        CalculatorParser1 parser = Parboiled.createParser(CalculatorParser1.class);
        while (true) {
            System.out.print("Enter an expression (3 + 5, single RETURN to exit)!\n");
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