package org.eggs.nodes;

/**
 * A Value node whose value is determined by one of its enumerated names in a set.
 * Example, "Monday", "Tuesday", "Wednesday", "Jan", "November"
 */
public class NamedValue extends Value {

    private Enum value;

    public NamedValue(NodeType nodeType, Enum namedEnum, String text) {
        super(nodeType, text);
        this.value = namedEnum;
    }

    @Override
    public Integer intValue() {
        return value.ordinal();
    }
}
