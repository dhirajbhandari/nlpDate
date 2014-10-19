package org.eggs.nodes;

import org.joda.time.DateTime;

import java.util.Arrays;

import static org.eggs.Logger.LOG;

public class DayMonthYear extends Node {

    private final Value year;
    private final Value month;
    private final Value date;

    public DayMonthYear(Node parent, Node yearNode, Node monthNode, Node node) {
        super(NodeType.DAY_MONTH_YEAR, parent, Arrays.asList(yearNode, monthNode, node));
        this.year = (Value) yearNode;
        this.month = (Value) monthNode;
        this.date = (Value) node;
    }

    @Override
    public DateTime getDate() {
        LOG.debug("Values: year: %s, month: %s, day: %s", year.intValue(), month.intValue(), date.intValue());
        return new DateTime(year.intValue(), month.intValue(), date.intValue(), 0, 0, 0);
    }
}
