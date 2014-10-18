package examples;

import org.parboiled.annotations.BuildParseTree;

import org.parboiled.Rule;
import org.parboiled.annotations.SuppressSubnodes;
import org.parboiled.support.Var;

/**
 * A calculator parser building an AST representing the expression structure before performing the actual calculation.
 * The value field of the parse nodes nodes is used for AST nodes.
 * As opposed to the examples.CalculatorParser2 this parser also supports floating point operations, negative numbers, a "power"
 * and a "SQRT" operation as well as optional whitespace between the various expressions components.
 */
@BuildParseTree
public class CalculatorParser4 extends CalculatorParser<CalcNode> {

    @Override
    public Rule InputLine() {
        return Sequence(Expression(), EOI);
    }

    Rule Expression() {
        return OperatorRule(Term(), FirstOf("+ ", "- "));
    }

    Rule Term() {
        return OperatorRule(Factor(), FirstOf("* ", "/ "));
    }

    Rule Factor() {
        return OperatorRule(Atom(), toRule("^ "));
    }

    Rule OperatorRule(Rule subRule, Rule operatorRule) {
        Var<Character> op = new Var<Character>();
        return Sequence(
                subRule,
                ZeroOrMore(
                        operatorRule,
                        op.set(matchedChar()),
                        subRule,
                        push(new CalcNode(op.get(), pop(1), pop()))
                )
        );
    }

    Rule Atom() {
        return FirstOf(
                Number(), Parens(), SquareRoot()
        );
    }

    Rule Parens() {
        return Sequence(
                "( ", Expression(), ") "
        );
    }

    Rule SquareRoot() {
        return Sequence("SQRT ", Parens(), push(new CalcNode('R', pop(), null)));
    }

    @SuppressSubnodes
    Rule Number() {
        return Sequence(
                FirstOf(
                        Sequence(
                                OptWhiteSpace(), Optional('-'), Digits(), Optional('.', Digits())
                        ),
                        Sequence(
                                OptWhiteSpace(), Optional('-'), '.', Digits()
                        )
                ),
                push(new CalcNode(Double.parseDouble(matchOrDefault("0")))),
                OptWhiteSpace()
        );
    }

    @SuppressSubnodes
    Rule Digits() {
        return OneOrMore(Digit());
    }

    Rule Digit() {
        return CharRange('0', '9');
    }


    // we redefine the rule creation for string literals to automatically match trailing whitespace if the string
    // literal ends with a space character, this way we don't have to insert extra whitespace() rules after each
    // character or string literal
    @Override
    protected Rule fromStringLiteral(String text) {
        return text.endsWith(" ")
                ? Sequence(String(text.substring(0, text.length() - 1)), OptWhiteSpace())
                : String(text);
    }

    @SuppressSubnodes
    Rule WhiteSpace() {
        return OneOrMore(AnyOf(" \t\f"));
    }

    @SuppressSubnodes
    Rule OptWhiteSpace() {
        return ZeroOrMore(AnyOf(" \t\f"));
    }


    //**************** MAIN ****************

    public static void main(String[] args) {
        main(CalculatorParser4.class);
    }
}
