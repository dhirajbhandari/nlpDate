package examples;

import org.parboiled.trees.ImmutableBinaryTreeNode;

/**
 * Created by raj on 4/10/2014.
 */
public class CalcNode extends ImmutableBinaryTreeNode<CalcNode> {


    private Operator operator;
    private Number value;

    public CalcNode(Number value) {
        super(null, null);
        this.value = value;
    }

    public CalcNode(Character operator, CalcNode left, CalcNode right) {
        super(left, right);
        this.operator = Operator.from(operator);
    }

    public Integer getValue() {
        return intValue();
    }

    public Integer intValue() {
        if (operator == null) {
            return value.intValue();
        }
        switch (operator) {
            case PLUS:
                return left().intValue() + right().intValue();
            case MINUS:
                return left().intValue() - right().intValue();
            case MULTIPLY:
                return left().intValue() * right().intValue();
            case DIVIDE:
                return left().intValue() / right().intValue();
            case SQRT:
                return (int) Math.round(Math.sqrt(left().intValue()));
            case POWER:
                return (int) Math.pow(left().intValue(), right().intValue());
            default:
                throw new IllegalStateException();
        }
    }

    public Double doubleValue() {
        if (operator == null) {
            return value.doubleValue();
        }
        switch (operator) {
            case PLUS:
                return left().doubleValue() + right().doubleValue();
            case MINUS:
                return left().doubleValue() - right().doubleValue();
            case MULTIPLY:
                return left().doubleValue() * right().doubleValue();
            case DIVIDE:
                return left().doubleValue() / right().doubleValue();
            case SQRT:
                return Math.sqrt(left().doubleValue());
            case POWER:
                return Math.pow(left().doubleValue(), right().doubleValue());
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public String toString() {
        return (operator == null ? "Value " + value : "examples.Operator '" + operator + '\'') + " | " + doubleValue();
    }
}
