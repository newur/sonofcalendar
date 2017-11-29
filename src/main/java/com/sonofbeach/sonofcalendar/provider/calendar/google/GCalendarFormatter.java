package com.sonofbeach.sonofcalendar.provider.calendar.google;

import com.google.api.services.calendar.model.CalendarListEntry;
import com.sonofbeach.sonofcalendar.model.calendar.Calendar;
import com.sonofbeach.sonofcalendar.model.calendar.CalendarProvider;

import java.util.ArrayList;
import java.util.List;

public class GCalendarFormatter {

     public static List<Calendar> standardizeCalendars(List<CalendarListEntry> gCalendars){
        List<Calendar> calendars = new ArrayList<>();
        for (CalendarListEntry entry : gCalendars) {
            calendars.add(new Calendar(CalendarProvider.GOOGLE.getName(), entry.getId(), entry.getSummary()));
        }
        return calendars;
    }

}
