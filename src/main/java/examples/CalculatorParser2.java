package examples;

import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.annotations.SuppressSubnodes;
import org.parboiled.support.Var;

import static org.parboiled.support.ParseTreeUtils.printNodeTree;


@BuildParseTree
public class CalculatorParser2 extends CalculatorParser<CalcNode> {

    public Rule InputLine() {
        return Sequence(Expression(), EOI);
    }

    Rule Expression() {
        Var<Character> op = new Var<Character>(); // we use an action variable to hold the operator character
        return Sequence(
                Term(),
                ZeroOrMore(
                        AnyOf("+-"),
                        op.set(matchedChar()), // set the action variable to the matched operator char
                        Term(),
                        // create an AST node for the operation that was just matched
                        // we consume the two top stack elements and replace them with a new AST node
                        // we use an alternative technique to the one shown in examples.CalculatorParser1 to reverse
                        // the order of the two top value stack elements
                        swap() && push(new CalcNode(op.get(), pop(), pop()))
                )
        );
    }

    Rule Term() {
        Var<Character> op = new Var<Character>();
        return Sequence(
                Factor(),
                ZeroOrMore(
                        AnyOf("*/"),
                        op.set(matchedChar()),
                        Factor(),
                        swap() && push(new CalcNode(op.get(), pop(), pop()))
                )
        );
    }

    Rule Factor() {
        Var<?> digits = new Var<String>();
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
                push(new CalcNode(Integer.parseInt(matchOrDefault("0")))));
    }

    @SuppressSubnodes
    Rule Digits() {
        return OneOrMore(Digit());
    }


    Rule Digit() {
        return CharRange('0', '9');
    }

    public static void main(String[] args) {
        main(CalculatorParser2.class);
    }


}