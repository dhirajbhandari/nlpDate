package org.eggs.nodes;

import org.joda.time.DateTime;

public class MonthYear extends Node {
    private final Value year;
    private final Value month;

    public MonthYear(Node year, Node monthName) {
        super(NodeType.MONTH_YEAR);
        this.year = (Value) year;
        this.month = (Value) monthName;
    }

    @Override
    public DateTime getDate() {
        //start of the date
        return new DateTime(year.intValue(), month.intValue(), 1, 0, 0, 0);
    }

}
