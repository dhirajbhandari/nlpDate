package org.eggs.nodes;

import java.util.HashSet;
import java.util.Set;

public class Day extends NamedValue<Day.Name> {

    enum Name {
        MON(1, "monday"),
        TUE(2, "tuesday"),
        WED(3, "wednesday"),
        THU(4, "thursday"),
        FRI(5, "friday"),
        SAT(6, "saturday"),
        SUN(7, "sunday");

        private final Integer value;
        private final String fullName;

        Name(Integer value, String fullName) {
            this.value = value;
            this.fullName = fullName;
        }

        public Integer getValue() {
            return value;
        }
    }

    private Name day;

    public Day(String text) {
        super(NodeType.DAY_OF_THE_WEEK_NAMED, text);
        this.day = find(Name.values(), text);
    }

    public Integer intValue() {
        return day.getValue();
    }

    public Name value() {
        return day;
    }

    public static String[] matchers() {
        Set<String> regexs = new HashSet<String>();
        for(Name day: Name.values()) {
            regexs.add(day.fullName.toLowerCase());
            regexs.add(day.name().toLowerCase());
        }
        return regexs.toArray(new String[regexs.size()]);
    }
}
