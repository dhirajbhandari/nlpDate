package org.eggs.nodes;

import org.eggs.DateDistance;
import org.eggs.ParsingContext;

public class NodeFactory {

    private final ParsingContext parsingContext;

    public NodeFactory(ParsingContext parsingContext) {
        this.parsingContext = parsingContext;
    }

    /**
     * Builders for Creating DateNode from the Parser
     * @param matchedText
     * @return
     */
    public Node twoDigitYear(String matchedText) {
        return new Value(NodeType.TWO_DIGIT_YEAR, matchedText);
    }

    public Node fourDigitYear(CharSequence matchedText) {
        return new Value(NodeType.FOUR_DIGIT_YEAR, matchedText.toString());
    }

    public Node monthOrdinal(String matchedText) {
        return new Value(NodeType.MONTH_ORDINAL, matchedText);
    }

    public Node dayOfTheMonthOrdinal(String text) {
        return new Value(NodeType.DAY_OF_THE_MONTH, text);
    }

    public Node formalDate(Node year, Node month, Node date) {
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

    public static String[] monthNameMatcher() {
        return Month.matchers();
    }

    public static String[] dayNameMatcher() {
        return Day.matchers();
    }

    public Node monthYear(Node monthName, Node fourDigitYear) {
        return new MonthYear(fourDigitYear, monthName);
    }
}
