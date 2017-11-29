package com.sonofbeach.sonofcalendar.provider.calendar.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.sonofbeach.sonofcalendar.model.calendar.CalendarEvent;
import com.sonofbeach.sonofcalendar.provider.authorization.GAuthorizationService;
import com.sonofbeach.sonofcalendar.model.calendar.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GCalendarService {

    private final GAuthorizationService gAuthorizationService;

    @Autowired
    public GCalendarService(GAuthorizationService gAuthorizationService) {
        this.gAuthorizationService = gAuthorizationService;
    }

    public com.google.api.services.calendar.Calendar getCalendarService(String user) throws Exception {
        Credential credential = gAuthorizationService.getFreshCredential(user);
        return new com.google.api.services.calendar.Calendar.Builder(gAuthorizationService.getHTTP_TRANSPORT(),
                gAuthorizationService.getJSON_FACTORY(), credential)
                .setApplicationName(gAuthorizationService.getAPPLICATION_NAME()).build();
    }

    public List<CalendarEvent> getCalendarEvents(String username, String calendarID){
        List<Event> events = new ArrayList<>();
        try {
            com.google.api.services.calendar.Calendar service = this.getCalendarService(username);
            events.addAll(service.events()
                    .list(calendarID)
                    .setMaxResults(30)
                    .setTimeMin(new DateTime(new Date()))
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute()
                    .getItems());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GCalendarEventFormatter.standardizeCalendarEvents(events);
    }

    public List<Calendar> getCalendars(String username) {
        List<CalendarListEntry> calendars = new ArrayList<>();
        try {
            com.google.api.services.calendar.Calendar service = this.getCalendarService(username);
            calendars = service.calendarList()
                    .list()
                    .execute()
                    .getItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GCalendarFormatter.standardizeCalendars(calendars);
    }



}
