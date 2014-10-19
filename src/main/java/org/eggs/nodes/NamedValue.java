package org.eggs.nodes;

/**
 * A Value node whose value is determined by one of its enumerated names in a set.
 * Example, "Monday", "Tuesday", "Wednesday", "Jan", "November"
 */
public abstract class NamedValue<T extends Enum> extends Value {

    public NamedValue(NodeType nodeType, String text) {
        super(nodeType, text);
    }

    public abstract T value();

    protected T find(T[] values, String text) {
        text = text.trim().toLowerCase();
        if (text.length() >= 3) {
            text = text.substring(0, 3);
        }
        for (T e : values) {
            if (text.equals(e.name().toLowerCase())) {
                return e;
            }
        }
        throw new IllegalArgumentException(String.format("text '%s' does not match any values", text));
    }
}
