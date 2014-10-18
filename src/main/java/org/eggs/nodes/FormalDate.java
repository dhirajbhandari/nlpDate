package org.eggs.nodes;

import org.joda.time.DateTime;

import java.util.Arrays;

public class FormalDate extends Node {

    private final Value year;
    private final Value month;
    private final Value date;

    public FormalDate(Node parent, Node yearNode, Node monthNode, Node node) {
        super(NodeType.DAY_MONTH_YEAR, parent, Arrays.asList(yearNode, monthNode, node));
        this.year = (Value) yearNode;
        this.month = (Value) monthNode;
        this.date = (Value) node;
    }

    @Override
    public DateTime getDate() {
        System.out.printf("Values: year: %s, month: %s, day: %s", year.intValue(), month.intValue(), date.intValue());
        return new DateTime(year.intValue(), month.intValue(), date.intValue(), 0, 0, 0);
    }
}
