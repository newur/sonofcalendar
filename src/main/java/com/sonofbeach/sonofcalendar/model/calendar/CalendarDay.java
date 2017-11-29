package com.sonofbeach.sonofcalendar.model.calendar;

import com.sonofbeach.sonofcalendar.utils.DateFormatter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CalendarDay {

    private LocalDate day;
    private List<CalendarDayEvent> assignedCalendarEvents = new ArrayList<>();

    public CalendarDay(LocalDate day) {
        this.day = day;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public List<CalendarDayEvent> getAssignedCalendarEvents() {
        Collections.sort(this.assignedCalendarEvents, new Comparator<CalendarDayEvent>() {
            public int compare(CalendarDayEvent o1, CalendarDayEvent o2) {
                return Boolean.compare(!o1.getCalendarEvent().isAllday(),!o2.getCalendarEvent().isAllday());
            }
        });
        return assignedCalendarEvents;
    }

    public void setAssignedCalendarEvents(List<CalendarDayEvent> assignedCalendarEvents) {
        this.assignedCalendarEvents = assignedCalendarEvents;
    }

    public String getDayString(){
        return DateFormatter.getDayString(this.day);
    }

    public String getDayPrefix(){
        return DateFormatter.getDayPrefix(this.day);
    }
}
