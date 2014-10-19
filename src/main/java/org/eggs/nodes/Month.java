package org.eggs.nodes;

import java.util.HashSet;
import java.util.Set;

public class Month extends NamedValue<Month.MonthName> {

    public enum MonthName {
        JAN(1, "january"),
        FEB(2, "february"),
        MAR(3, "march"),
        APR(4, "april"),
        MAY(5, "may"),
        JUN(6, "june"),
        JUL(7, "july"),
        AUG(8, "august"),
        SEP(9, "september"),
        OCT(10, "october"),
        NOV(11, "november"),
        DEC(12, "december");

        private final Integer value;
        private final String fullName;

        private MonthName(Integer value, String fullName) {
            this.value = value;
            this.fullName = fullName;
        }

        public Integer getValue() {
            return value;
        }
    }

    private MonthName month;

    public Month(String text) {
        super(NodeType.MONTH_NAMED, text);
        this.month = find(MonthName.values(), text);
        System.out.println("Matchers");
        for(String s : Month.matchers()) {
            System.out.println("[" + s + "]");
        }
//            System.out.println(Month.matchers());
    }

    public Integer intValue() {
        return month.getValue();
    }

    public MonthName value() {
        return month;
    }

    public static String[] matchers() {
        Set<String> regexs = new HashSet<String>();
        for (MonthName monthName : MonthName.values()) {
            regexs.add(monthName.fullName.toLowerCase() + " ");
            regexs.add(monthName.name().toLowerCase() + " ");
        }
        return regexs.toArray(new String[regexs.size()]);
    }
}
