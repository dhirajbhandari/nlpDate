package org.eggs;

import org.eggs.nodes.Node;
import org.eggs.nodes.NodeFactory;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.annotations.SuppressSubnodes;
import org.parboiled.support.Var;

@BuildParseTree
public class DateParser extends BaseParser<Node> {

    protected NodeFactory nodeFactory;

    public void setParsingContext(ParsingContext parsingContext) {
        this.nodeFactory = new NodeFactory(parsingContext);
    }

    public Rule InputLine() {
        return Sequence(DateExpressions(), EOI);
    }

    Rule DateExpressions() {
        return OneOrMore(DateExpression());
    }

    Rule DateExpression() {
        return FirstOf(DateDuration(), DatePoint());
    }

    Rule DateDuration() {
        return NOTHING;
        //ignore Durations for now
        //return FirstOf(RelativeDuration(), FixedDuration());
    }

    Rule DatePoint() {
        return FirstOf(RelativeDatePoint(), FixedDatePoint());
    }

    /**
     * Examples:
     * beginning of last month
     * start of the year
     * start of this year
     * start of next week
     */
    Rule StartOfRelativeDatePoint() {
        return Sequence(FirstOf("start of ", "starting ", "beginning of "), RelativeDatePoint());
    }

    /**
     * Examples:
     * end of next year, end of next month, end of last month, end of next week
     */
    Rule EndOfRelativeDatePoint() {
        return Sequence(FirstOf("end of ", "ending "), RelativeDatePoint());
    }

    Rule RelativeDatePoint() {
        Var<String> pointer = new Var<String>(); //this, next, last
        Var<String> unit = new Var<String>(); //week, month etc
        return FirstOf(
                Sequence(FirstOf("yesterday ", "today ", "tomorrow ", "tonight "), push(nodeFactory.relativeDatePoint(match()))),
                Sequence(
                        Sequence(
                                FirstOf("this", "last", "next"), pointer.set(match()),
                                WhiteSpace(),
                                FirstOf("week", "month", "year", "decade", "century"), unit.set(match())
                        ),
                        push(nodeFactory.relativeDatePoint(pointer.get(), unit.get()))
                )
        );
    }

    Rule FixedDatePoint() {
        return FirstOf(FullDayMonthYearDatePoint(), MonthNameYear());
    }

    Rule FullDayMonthYearDatePoint() {
        return FirstOf(
                Sequence( // "14-01-2014", "14/01/2014", "14/01/14"
                        DateOrdinal(), DateFieldSeparator(),
                        MonthOrdinal(), DateFieldSeparator(),
                        FirstOf(FourDigitYearOrdinal(), TwoDigitYearOrdinal()),
                        push(nodeFactory.yearMonthDate(pop(), pop(), pop()))
                ),
                Sequence(  // "february 14, 2004",  "14 feb, 2004"
                        Sequence(DayAndMonth(), FourDigitYearOrdinal()),
                        push(nodeFactory.yearMonthDate(pop(), pop(), pop()))
                ),
                Sequence(  // "may 27th 2014", 1st Jan 2011
                        Sequence(DayAndMonth(), FourDigitYearOrdinal()),
                        push(nodeFactory.yearMonthDate(pop(), pop(), pop()))
                )

        );
    }

    // "March 2001", "Feb 2009"
    Rule MonthNameYear() {
        return Sequence(
                Sequence(MonthName(), FourDigitYearOrdinal()),
                push(nodeFactory.monthYear(pop(1), pop()))
        );
    }


    // "Feb", "mar", "December"
    Rule MonthName() {
        return Sequence(
                MonthNames(),
//                FirstOf(Month.matchers()),
                push(nodeFactory.monthName(match()))
        );
    }

    /**
     *
     * "14 February", "February 14", "may 27th 2014", 1st Jan 2011
     * puts 2 nodes on stack with month on top like so:
     * <pre>
     *         [Top ------------------ Bottom]
     *         [Month, DateOrdinal           ]
     * </pre>
     */
    Rule DayAndMonth() {
        return FirstOf(
                Sequence(
                        Sequence(MonthName(), OptWhiteSpace(), DateOrdinal(), Optional(','), OptWhiteSpace()),
                        pushAll(pop(), pop()) //push it back as (month(top), date(top-1)
                ),
                Sequence(DateOrdinal(), WhiteSpace(), MonthName(), Optional(','), OptWhiteSpace())
        );
    }

    Rule MonthNames() {
        return FirstOf(
                "january ", "jan ",
                "february ", "feb ",
                "march ", "mar ",
                "april ", "apr ",
                "may ",
                "june ", "jun ",
                "july ", "jul ",
                "august ", "aug ",
                "september ", "sep ",
                "october ", "oct ",
                "november ", "nov ",
                "december ", "dec "
        );
    }

    //mon, tue, saturday
    Rule DayName() {
        return Sequence(
                DayOfTheWeekNames(),
//                FirstOf(nodeFactory.dayNameMatcher()),
                push(nodeFactory.dayName(match()))
        );
    }

    Rule DayOfTheWeekNames() {
        return FirstOf(
                "monday ", "mon ",
                "tuesday ", "tue ",
                "wednesday ", "wed ",
                "thursday ", "thu ",
                "friday ", "fri",
                "saturday ", "sat "
        );
    }

    Rule DateFieldSeparator() {
        return FirstOf("- ", "/ ");
    }

    Rule WhiteSpace() {
        return OneOrMore(AnyOf(" \t\f"));
    }

    Rule OptWhiteSpace() {
        return ZeroOrMore(AnyOf(" \t\f"));
    }

    @SuppressSubnodes
    Rule Digits() {
        return OneOrMore(Digit());
    }

    Rule NonZeroDigit() {
        return CharRange('1', '9');
    }

    // Matches 0001 - 9999
    @SuppressSubnodes
    Rule FourDigitYearOrdinal() {
        return Sequence(
                //Wrap it with a inner Sequence, so that so we can easily access
                // the input text matched by Rules inside with "match()" or "matchOrDefault()"
                Sequence(Digit(), Digit(), Digit(), Digit()),
                push(nodeFactory.fourDigitYear(matchOrDefault("0")))
        );
    }

    // Matches 01 - 99 (year 2000 assumed)
    @SuppressSubnodes
    Rule TwoDigitYearOrdinal() {
        return Sequence(
                Sequence(Digit(), Digit()),
                push(nodeFactory.twoDigitYear(matchOrDefault("0")))
        );
    }

    // Matches 01 - 12
    @SuppressSubnodes
    Rule MonthOrdinal() {
        return Sequence(
                FirstOf(
                        Sequence("1", CharRange('0', '2')),
                        Sequence(Optional('0'), NonZeroDigit())
                        ),
                push(nodeFactory.monthOrdinal(matchOrDefault("0")))
        );
    }

    // Matches DayOfTheMonth (01 - 31)
    // todo: "1st", "2nd", "3rd", "4th", "23rd", "31st"
//    @SuppressSubnodes
//    Rule DateOrdinal() {
//        return Sequence(
//                FirstOf(
//                        Sequence(CharRange('1', '2'), Digit()),
//                        Sequence('3', CharRange('0', '1')),
//                        Sequence(Optional('0'), NonZeroDigit())
//                ),
//                push(nodeFactory.dayOfTheMonthOrdinal(matchOrDefault("0")))
//        );
//    }

    @SuppressSubnodes
    Rule DateOrdinal() {
        return Sequence(
                FirstOf(
                        Sequence(FirstOf('1', "21", "31"), Optional("st")),
                        Sequence(FirstOf('2', "22"), Optional("nd")),
                        Sequence(FirstOf('3', "23"), Optional("rd")),
                        Sequence(CharRange('4', '9'), Optional("th")),
                        Sequence('1', Digit(), Optional("th")),
                        Sequence(FirstOf("20", "30"), Optional("th")),
                        Sequence('2', CharRange('4', '9'), Optional("th")),
                        Sequence('0', NonZeroDigit())
                ),
                push(nodeFactory.dayOfTheMonthOrdinal(matchOrDefault("0")))
        );
    }

    @SuppressSubnodes
    Rule Digit() {
        return CharRange('0', '9');
    }

    // we redefine the rule creation for string literals to automatically match trailing whitespace if the string
    // literal ends with a space character, this way we don't have to insert extra whitespace() rules after each
    // character or string literal
    @Override
    protected Rule fromStringLiteral(String text) {
        return text.endsWith(" ")
                ? Sequence(String(text.substring(0, text.length() - 1)), OptWhiteSpace())
                : String(text);
    }

    //Durations
//    Rule RelativeDuration() {
//        return FirstOf(
//                FromToRelativeDate(),
//                HyphenRelativeDate()
//        );
//    }
//
//    Rule FixedDuration() {
//        return Sequence(NonZeroDigit(), ZeroOrMore(Digits()), WhiteSpace(), FirstOf("M ", "H "));
//    }
//
//    Rule FromToRelativeDate() {
//        return Sequence(
//                Optional(IgnoreCase("from")),
//                RelativeDatePoint(),
//                IgnoreCase("to"),
//                RelativeDatePoint()
//        );
//    }
//
//    Rule HyphenRelativeDate() {
//        return Sequence(
//                RelativeDatePoint(),
//                WhiteSpace(),
//                IgnoreCase("- "),
//                RelativeDatePoint()
//        );
//    }
}