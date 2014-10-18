package examples;

public enum Operator {
    PLUS('+'), MINUS('-'), MULTIPLY('*'), DIVIDE('/'), SQRT('R'), POWER('^');

    private final Character operator;

    Operator(Character operator) {
        this.operator = operator;
    }

    public static Operator from(Character op) {
        for (Operator operator : values()) {
            if (operator.operator == op) return operator;
        }
        throw new IllegalArgumentException("not a valid operator: " + op);
    }
}
