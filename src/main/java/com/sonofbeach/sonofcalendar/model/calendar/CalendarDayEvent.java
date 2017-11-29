package com.sonofbeach.sonofcalendar.model.calendar;

import static java.time.temporal.ChronoUnit.DAYS;

public class CalendarDayEvent {

    private CalendarDay calendarDay;
    private CalendarDayEventType type;
    private CalendarEvent calendarEvent;
    private int dayNo;

    public CalendarDayEvent(CalendarDay calendarDay, CalendarDayEventType type, CalendarEvent calendarEvent) {
        this.calendarDay = calendarDay;
        this.type = type;
        this.calendarEvent = calendarEvent;
        this.dayNo = (int) DAYS.between(calendarEvent.getStart().toLocalDate(), calendarDay.getDay()) + 1;
    }

    public CalendarDayEventType getType() {
        return type;
    }

    public void setType(CalendarDayEventType type) {
        this.type = type;
    }

    public CalendarEvent getCalendarEvent() {
        return calendarEvent;
    }

    public void setCalendarEvent(CalendarEvent calendarEvent) {
        this.calendarEvent = calendarEvent;
    }

    public String getPrefix(){
        switch(type) {
            case START:     return calendarEvent.getStartAsString();
            case NORMAL:    return calendarEvent.getStartAsString() + " - " + calendarEvent.getEndAsString();
            default:        return "";
        }
    }

    public String getSuffix(){
        switch(type) {
            case END:       return "(" + this.dayNo + "/" + calendarEvent.getDaysNo() + " Ende: " + calendarEvent.getEndAsString() + ")";
            case START:     return "(" + this.dayNo + "/" + calendarEvent.getDaysNo() + ")";
            case MIDDLE:    return "(" + this.dayNo + "/" + calendarEvent.getDaysNo() + ")";
            default:        return "";
        }
    }
}


