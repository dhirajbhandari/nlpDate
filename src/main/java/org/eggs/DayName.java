package org.eggs;

public enum DayName implements Matchable {

    MON("monday"),
    TUE("tuesday"),
    WED("wednesday"),
    THU("thursday"),
    FRI("friday"),
    SAT("saturday"),
    SUN("sunday");

    private final String fullName;
    private final String[] matchers;

    private DayName(String fullName) {
        this.fullName = fullName;
        this.matchers = new String[]{name().toLowerCase(), fullName};
    }

    public static DayName match(String text) {
        text = text.trim().toUpperCase();
        if (text.length() >= 3) {
            String name = text.substring(0, 3);
            return valueOf(name);
        }
        throw new IllegalArgumentException(String.format("text '%s' does not match any day", text));
    }

    public String[] matchers() {
        return matchers;
    }
}


