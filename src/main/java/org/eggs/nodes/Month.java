package org.eggs.nodes;

import java.util.HashSet;
import java.util.Set;

public class Month extends NamedValue<Month.Name> {

    enum Name {
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

        private Name(Integer value, String fullName) {
            this.value = value;
            this.fullName = fullName;
        }

        public Integer getValue() {
            return value;
        }
    }

    private Name month;

    public Month(String text) {
        super(NodeType.MONTH_NAMED, text);
        this.month = find(Name.values(), text);
    }

    public Integer intValue() {
        return month.getValue();
    }

    public Name value() {
        return month;
    }

    public static String[] matchers() {
        Set<String> regexs = new HashSet<String>();
        for (Name name : Name.values()) {
            regexs.add(name.fullName.toLowerCase() + " ");
            regexs.add(name.name().toLowerCase() + " ");
        }
        return regexs.toArray(new String[regexs.size()]);
    }
}
