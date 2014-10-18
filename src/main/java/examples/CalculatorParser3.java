package examples;

import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.annotations.SuppressSubnodes;
import org.parboiled.support.Var;

/**
 * A calculator parser building an AST representing the expression structure before performing the actual calculation.
 * The value field of the parse nodes nodes is used for AST nodes.
 * As opposed to the examples.CalculatorParser2 this parser also supports floating point operations, negative numbers, a "power"
 * and a "SQRT" operation as well as optional whitespace between the various expressions components.
 */
@BuildParseTree
public class CalculatorParser3 extends CalculatorParser<CalcNode> {

    public Rule InputLine() {
        return Sequence(Expression(), EOI);
    }

    Rule Expression() {
       Var<Character> op = new Var<Character>();
       return Sequence(
               Term(),
               ZeroOrMore(
                       // we use a FirstOf(String, String) instead of a AnyOf(String) so we can use the
                       // fromStringLiteral transformation (see below), which automatically consumes trailing whitespace
                       FirstOf("+ ", "- "), op.set(matchedChar()),
                       Term(),
                       swap() && push(new CalcNode(op.get(), pop(), pop()))
               )
       );
    }

    Rule Term() {
       Var<Character> op = new Var<Character>();
       return Sequence(
               Factor(),
               ZeroOrMore(
                       FirstOf("* ", "/ "), op.set(matchedChar()),
                       Factor(),
                       swap() && push(new CalcNode(op.get(), pop(), pop()))
               )
       );
    }

    Rule Factor() {
        return Sequence(
                Atom(),
                ZeroOrMore(
                        "^ ",
                        Atom(),
                        push(new CalcNode('^', pop(1), pop()))
                )
        );
    }

    Rule Atom() {
        return FirstOf(Number(), SquareRoot(), Parens());
    }

    Rule SquareRoot() {
        return Sequence(
                "SQRT ",
                Parens(),

                // create a new AST node with a special operator 'R' and only one child
                push(new CalcNode('R', pop(), null))
            );
    }

    Rule Parens() {
        return Sequence("( ", Expression(), ") ");
    }

    Rule Number() {
        return Sequence(
                // we use an 'outer' Sequence outside the FirstOf "Number" Sequence so we can easily access
                // the input text matched by Rules inside FirstOf with "match()" or "matchOrDefault()"
                FirstOf(
                        Sequence(
                                Optional('-'),
                                Digits(),
                                Optional('.', Digits())
                        ),
                        Sequence(
                                Optional('-'),
                                '.',
                                Digits()
                        )
                ),

                // the matchOrDefault() call returns the matched input text of the immediately preceding rule
                // or a default string (in this case if it is run during error recovery (resynchronization))
                push(new CalcNode(Double.parseDouble(matchOrDefault("0")))),
                WhiteSpace()
        );
    }

    @SuppressSubnodes
    Rule Digits() {
        return OneOrMore(Digit());
    }

    Rule Digit() {
        return CharRange('0', '9');
    }

    @SuppressSubnodes
    Rule WhiteSpace() {
        return ZeroOrMore(AnyOf(" \t\f"));
    }

    // we redefine the rule creation for string literals to automatically match trailing whitespace if the string
    // literal ends with a space character, this way we don't have to insert extra whitespace() rules after each
    // character or string literal
    @Override
    protected Rule fromStringLiteral(String literal) {
       return literal.endsWith(" ")
               ? Sequence(String(literal.substring(0, literal.length() - 1)), WhiteSpace())
               : String(literal);
    }

    //**************** MAIN ****************

    public static void main(String[] args) {
        main(CalculatorParser3.class);
    }
}
