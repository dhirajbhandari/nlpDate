package examples;

import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.annotations.SuppressSubnodes;


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

}