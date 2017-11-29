package com.sonofbeach.sonofcalendar.model.calendar;


public enum CalendarProvider {

    GOOGLE("google"),
    ICALENDAR("icalendar");

    private String name;

    CalendarProvider(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
