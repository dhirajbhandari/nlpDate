package org.eggs;

import org.joda.time.*;

import java.util.HashMap;
import java.util.Map;

public class DateDistance {

    private final DurationFieldType fieldType;
    private final int amount;

    private static Map<String, DurationFieldType> nameToDurationFieldMap = buildNameToDurationFieldMap();

    public DateDistance(DurationFieldType durationFieldType, int amount) {
        this.fieldType = durationFieldType;
        this.amount = amount;
    }

    public Period getPeriod() {
        return new Period().withField(fieldType, amount);
    }

    public DateTime dateWhenCurrentAt(DateTime currentDateTime) {
        MutableDateTime mtd = currentDateTime.toMutableDateTime();
        mtd.add(fieldType, amount);
        return mtd.toDateTime();
    }

    public static DateDistance parse(String pointerStr, String durationFieldStr) {
        DurationFieldType fieldType = nameToDurationFieldMap.get(durationFieldStr);
        if (fieldType == null) {
            throw new RuntimeException("unable to parse duration string " + durationFieldStr);
        }
        pointerStr = pointerStr.trim().toLowerCase();

        int amount;

        if ("last".equals(pointerStr)) {
            amount = -1;
        } else if ("next".equals(pointerStr)) {
            amount = 1;
        } else { //if ("this".equals(pointerStr)) {
            amount = 0;
        }

        return new DateDistance(fieldType, amount);
    }

    private static Map<String, DurationFieldType> buildNameToDurationFieldMap() {
        Map<String, DurationFieldType> map = new HashMap<String, DurationFieldType>();
        map.put("century", DurationFieldType.centuries());
        map.put("year", DurationFieldType.years());
        map.put("month", DurationFieldType.months());
        map.put("week", DurationFieldType.weeks());
        map.put("day", DurationFieldType.days());
        map.put("hour", DurationFieldType.hours());
        map.put("minute", DurationFieldType.minutes());
        map.put("second", DurationFieldType.seconds());
        //three years, six months,
        // four days, twelve hours, thirty minutes, and five seconds
        return map;
    }

    public static DateDistance parseDayPointer(String dayPointer) {
        dayPointer = dayPointer.trim().toLowerCase();
        int amount;
        if ("tomorrow".equals(dayPointer)) {
            amount = 1;
        } else if ("yesterday".equals(dayPointer)) {
            amount = -1;
        } else { //if("today".equals(dayPointer)) {
            amount = 0;
        }
        return new DateDistance(DurationFieldType.days(), amount);
    }
}
