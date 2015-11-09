package org.eggs.nodes;

import static org.eggs.Logger.LOG;

public class Value extends Node {

    private String text;

    public Value(NodeType nodeType, String text) {
        super(nodeType, null, null);
        this.text = text;
    }

    public Integer intValue() {
        LOG.debug("Value#<@text: %s>.intValue()\n", text);
        Integer val = Integer.parseInt(text);
        if (NodeType.TWO_DIGIT_YEAR.equals(getNodeType()))  {
            val = val + 2000; //current century
        }
        LOG.debug("raw text: %s value: %d", text, val);
        return val;
    }
}
