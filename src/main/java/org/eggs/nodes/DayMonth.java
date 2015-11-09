package org.eggs.nodes;

import org.eggs.ParsingContext;
import org.joda.time.DateTime;

import java.util.Arrays;

import static org.eggs.Logger.LOG;

public class DayMonth extends Node {

    private final Value month;
    private final Value date;
    private final int year;

    public DayMonth(ParsingContext context, Node monthNode, Node node) {
        super(NodeType.DAY_MONTH, null, Arrays.asList(monthNode, node));
        this.year = context.getCurrentDateTime().getYear();
        this.month = (Value) monthNode;
        this.date = (Value) node;
    }

    @Override
    public DateTime getDate() {
        LOG.debug("Values: year: %s, month: %s, day: %s", year, month.intValue(), date.intValue());
        return new DateTime(year, month.intValue(), date.intValue(), 0, 0, 0);
    }
}
