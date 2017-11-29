package com.sonofbeach.sonofcalendar.provider.calendar.ical4j;

import biweekly.Biweekly;
import biweekly.ICalendar;
import com.sonofbeach.sonofcalendar.model.calendar.CalendarEvent;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@Service
public class BiweeklyService {

    private ICalendar buildCalendar(String url) throws IOException {
        InputStream fin = new URL(url).openStream();
        return Biweekly.parse(fin).first();
    }

    public List<CalendarEvent> getCalendarEvents(String url){
        ICalendar calendar = null;
        try {
            calendar = this.buildCalendar(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BiweeklyEventFormatter.standardizeCalendarEvents(calendar);
    }



}
