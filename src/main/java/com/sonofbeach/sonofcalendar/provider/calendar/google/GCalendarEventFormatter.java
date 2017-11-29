package com.sonofbeach.sonofcalendar.provider.calendar.google;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.sonofbeach.sonofcalendar.model.calendar.CalendarEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class GCalendarEventFormatter {

        static List<CalendarEvent> standardizeCalendarEvents(List<Event> events){
            List<CalendarEvent> calendarEvents= new ArrayList<>();
            for(Event event : events){
                calendarEvents.add(new CalendarEvent(event.getSummary(), event.getDescription(), event.getLocation(),
                        eventDateTimeToLocalDateTime(event.getStart()), eventDateTimeToLocalDateTime(event.getEnd())));
            }
            return calendarEvents;
        }        

        private static LocalDateTime eventDateTimeToLocalDateTime(EventDateTime datetime){
        if(datetime==null)
            return null;
        else if(datetime.getDateTime()==null && datetime.getDate()!=null) {
            DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDateTime.of(LocalDate.parse(datetime.getDate().toStringRfc3339(), DATEFORMATTER),
                    LocalDateTime.MIN.toLocalTime());
        }
        else{
            DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
            return LocalDateTime.parse(datetime.getDateTime().toStringRfc3339(), DATEFORMATTER);
        }
    }


}
