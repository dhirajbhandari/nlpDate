package org.eggs.nodes;

import org.eggs.DateDistance;
import org.eggs.ParsingContext;

/**
 * Builder for Creating Nodes from the Parser
 */
public class NodeFactory {

    private final ParsingContext parsingContext;

    public NodeFactory(ParsingContext parsingContext) {
        this.parsingContext = parsingContext;
    }

    public Node twoDigitYear(String matchedText) {
        return new Year(NodeType.TWO_DIGIT_YEAR, matchedText);
    }

    public Node fourDigitYear(CharSequence matchedText) {
        return new Year(NodeType.FOUR_DIGIT_YEAR, matchedText.toString());
    }

    public Node monthOrdinal(String matchedText) {
        return new Value(NodeType.MONTH_ORDINAL, matchedText);
    }

    public Node dayOfTheMonthOrdinal(String text) {
        //System.out.println("dayOfTheMonthOrdinal(text:" + text);
        return new Value(NodeType.DAY_OF_THE_MONTH, removeMonthSuffix(text.toString()));
    }

    public Node formalDate(Node year, Node month, Node date) {
        return new DayMonthYear(null, year, month, date);
    }

    public Node yearMonthDate(Node year, Node month, Node date) {
        return new DayMonthYear(null, year, month, date);
    }

    public Node relativeDatePoint(String pointer, String unit) {
        return new RelativeDate(parsingContext, DateDistance.parse(pointer, unit));
    }

    public Node relativeDatePoint(String dayPointer) {
        String[] splits = dayPointer.split("\\s+");
        if (splits.length == 2) {
            return relativeDatePoint(splits[0], splits[1]);
        } else {
            return new RelativeDate(parsingContext, DateDistance.parseDayPointer(dayPointer));
        }
    }

    public Node monthName(String monthStr) {
        return new Month(monthStr);
    }

    public Node dayName(String dayStr) {
        return new Day(dayStr);
    }

    public Node monthYear(Node monthName, Node fourDigitYear) {
        return new MonthYear(fourDigitYear, monthName);
    }

    public Node monthDate(Node monthName, Node dateOrdinal) {
        return new DayMonth(parsingContext, monthName, dateOrdinal);
    }

    public String removeMonthSuffix(String text) {
        return text.replaceAll("(st|nd|rd|th)","");
    }
}
