package org.eggs.nodes;

public class Value extends Node {

    private String text;

    public Value(NodeType nodeType, String text) {
        super(nodeType, null, null);
        this.text = text;
    }

    public Integer intValue() {
        Integer val = Integer.parseInt(text);
        if (NodeType.TWO_DIGIT_YEAR.equals(getNodeType()))  {
            val = val + 2000; //current century
        }
        System.out.printf("raw text: %s value: %d", text, val);
        return val;
    }
}