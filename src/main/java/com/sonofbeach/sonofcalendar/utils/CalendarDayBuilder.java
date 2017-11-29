package com.sonofbeach.sonofcalendar.utils;

import com.sonofbeach.sonofcalendar.model.calendar.CalendarDay;
import com.sonofbeach.sonofcalendar.model.calendar.CalendarDayEvent;
import com.sonofbeach.sonofcalendar.model.calendar.CalendarDayEventType;
import com.sonofbeach.sonofcalendar.model.calendar.CalendarEvent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarDayBuilder {

    public static List<CalendarDay> getCalendarDays(List<CalendarEvent> calendarEvents){
        List<CalendarDay> calendarDays = new ArrayList<>();
        for(CalendarEvent calendarEvent : calendarEvents){
            calendarDays = updateCalendarDaysForEvent(calendarEvent, calendarDays);
        }
        return calendarDays;
    }

    private static List<CalendarDay> updateCalendarDaysForEvent(CalendarEvent calendarEvent, List<CalendarDay> calendarDays){
        if(calendarEvent.isAllday()){
            calendarDays = addEventToDay(calendarEvent.getStart().toLocalDate(), calendarEvent,
                    calendarDays, CalendarDayEventType.ALLDAY);
        }
        else if (calendarEvent.isMultiday()) {
            calendarDays = addEventToDay(calendarEvent.getStart().toLocalDate(), calendarEvent,
                    calendarDays, CalendarDayEventType.START);
            calendarDays = addEventToDay(calendarEvent.getEnd().toLocalDate(), calendarEvent,
                    calendarDays, CalendarDayEventType.END);
            for(LocalDate day : calendarEvent.getMiddleDays())
                calendarDays = addEventToDay(day, calendarEvent,
                        calendarDays, CalendarDayEventType.MIDDLE);
        }
        else{
            calendarDays = addEventToDay(calendarEvent.getStart().toLocalDate(), calendarEvent,
                    calendarDays, CalendarDayEventType.NORMAL);
        }
        return calendarDays;
    }

    private static List<CalendarDay> addEventToDay(LocalDate day, CalendarEvent calendarEvent,
                                                   List<CalendarDay> calendarDays, CalendarDayEventType type){
        boolean dayExists=false;
        for(CalendarDay calendarDay : calendarDays){
            if(calendarDay.getDay().equals(day)) {
                dayExists=true;
                calendarDay.getAssignedCalendarEvents().add(new CalendarDayEvent(calendarDay,type,calendarEvent));
            }
        }
        if(!dayExists){
            CalendarDay calendarDay = new CalendarDay(day);
            calendarDay.getAssignedCalendarEvents().add(new CalendarDayEvent(calendarDay,type,calendarEvent));
            calendarDays.add(calendarDay);
        }
        return calendarDays;
    }

}
