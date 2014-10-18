package org.eggs.nodes;

import org.eggs.DateDistance;
import org.eggs.ParsingContext;
import org.joda.time.DateTime;

public class RelativeDate extends Node {

    private final DateDistance dateDistance;
    private final DateTime currentTime;

    public RelativeDate(ParsingContext parsingContext, DateDistance distance) {
        super(NodeType.RELATIVE_DATE, null, null);
        this.currentTime = parsingContext.getCurrentDateTime();
        this.dateDistance = distance;
    }

    @Override
    public DateTime getDate() {
        return dateDistance.dateWhenCurrentAt(currentTime);
    }
}
