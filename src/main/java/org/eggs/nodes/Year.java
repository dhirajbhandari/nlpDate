package org.eggs.nodes;

import static org.eggs.Logger.LOG;

public class Year extends Value {

    private final int value;

    public Year(NodeType nodeType, String text) {
        super(nodeType, text);
        this.value = Integer.parseInt(text);
        LOG.debug("raw text: %s value: %d", text, value);
    }

    public Integer intValue() {
        return (NodeType.TWO_DIGIT_YEAR.equals(getNodeType())) ?
                value + 2000 //current century
                : value;
    }
}
