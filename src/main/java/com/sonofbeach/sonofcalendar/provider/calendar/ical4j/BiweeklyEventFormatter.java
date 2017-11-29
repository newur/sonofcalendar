package com.sonofbeach.sonofcalendar.provider.calendar.ical4j;

import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.io.TimezoneAssignment;
import biweekly.io.TimezoneInfo;
import biweekly.util.com.google.ical.compat.javautil.DateIterator;
import com.sonofbeach.sonofcalendar.model.calendar.CalendarEvent;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

class BiweeklyEventFormatter {

    static List<CalendarEvent> standardizeCalendarEvents(ICalendar iCalendar){
        List<CalendarEvent> calendarEvents= new ArrayList<>();
        for(VEvent vEvent : iCalendar.getEvents()){
            calendarEvents = getEventsFromRecurringEvent(iCalendar,vEvent,calendarEvents);
        }
        return calendarEvents;
    }

    private static LocalDateTime dateToLocalDateTime(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static List<CalendarEvent> getEventsFromRecurringEvent(ICalendar iCalendar, VEvent vEvent, List<CalendarEvent> calendarEvents){
        TimezoneInfo tzinfo = iCalendar.getTimezoneInfo();
        TimeZone timezone;
        if (tzinfo.isFloating(vEvent.getDateStart())){
            timezone = TimeZone.getDefault();
        } else {
            TimezoneAssignment dtstartTimezone = tzinfo.getTimezone(vEvent.getDateStart());
            timezone = (dtstartTimezone == null) ? TimeZone.getTimeZone("UTC") : dtstartTimezone.getTimeZone();
        }
        DateIterator it = vEvent.getDateIterator(timezone);
        int max=30;
        while(it.hasNext() && max>0){
            //Date calculation
            Date startDate = it.next();
            long difference = vEvent.getDateEnd().getValue().getTime()-vEvent.getDateStart().getValue().getTime();
            Date endDate = new Date(startDate.getTime() + difference);
            Date now = new Date();

            //Add event only if event is happening now or in the future
            if(endDate.getTime()>=now.getTime()){
                calendarEvents.add(new CalendarEvent(vEvent.getSummary().getValue(), vEvent.getDescription().getValue(),
                        vEvent.getLocation().getValue(), dateToLocalDateTime(startDate),
                        dateToLocalDateTime(endDate)));
            }

            max--;
        }
        return calendarEvents;
    }

}
