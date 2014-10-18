package org.eggs;

import org.joda.time.DateTime;

import java.util.Locale;
import java.util.TimeZone;

public class ParsingContext {
    private DateTime currentDateTime;
    private TimeZone timeZone;
    private Locale locale;

    public ParsingContext() {
        this(new DateTime());
    }

    public ParsingContext(DateTime currentDateTime) {
        this.currentDateTime = currentDateTime;
        this.timeZone = TimeZone.getDefault();
        this.locale = Locale.getDefault();
    }

    public DateTime getCurrentDateTime() {
        return currentDateTime;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }
}
