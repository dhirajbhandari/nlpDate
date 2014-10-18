package org.eggs;

public enum MonthName implements Matchable {

    JAN("january"),
    FEB("february"),
    MAR("march"),
    APR("april"),
    MAY("may"),
    JUN("june"),
    JUL("july"),
    AUG("august"),
    SEP("september"),
    OCT("october"),
    NOV("november"),
    DEC("december");

    private final String fullName;
    private final String[] matchers;

    private MonthName(String fullName) {
        this.fullName = fullName;
        this.matchers = new String[]{name(), fullName};
    }

    public static MonthName match(String text) {
        text = text.trim().toUpperCase();
        if (text.length() >= 3) {
            String name = text.substring(0, 3);
            return valueOf(name);
        }
        throw new IllegalArgumentException(String.format("text '%s' does not match any month", text));
    }

    public String[] matchers() {
        return matchers;
    }


}


