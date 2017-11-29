package com.sonofbeach.sonofcalendar.services;

import com.sonofbeach.sonofcalendar.database.UserProvider;
import com.sonofbeach.sonofcalendar.model.calendar.CalendarDay;
import com.sonofbeach.sonofcalendar.model.calendar.CalendarEvent;
import com.sonofbeach.sonofcalendar.model.calendar.CalendarProvider;
import com.sonofbeach.sonofcalendar.model.user.User;
import com.sonofbeach.sonofcalendar.provider.calendar.google.GCalendarService;
import com.sonofbeach.sonofcalendar.provider.calendar.ical4j.BiweeklyService;
import com.sonofbeach.sonofcalendar.utils.CalendarEventAdapter;
import com.sonofbeach.sonofcalendar.model.calendar.Calendar;
import com.sonofbeach.sonofcalendar.utils.CalendarDayBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RestController
public class CalendarService {

    private final GCalendarService gCalendarService;
    private final UserProvider userProvider;

    @Autowired
    public CalendarService(GCalendarService gCalendarService, UserProvider userProvider) {
        this.gCalendarService = gCalendarService;
        this.userProvider = userProvider;
    }

    @RequestMapping("/restGetCalendarEvents")
    public List<CalendarEvent> getAllCalendarEvents(){
        User user = userProvider.getUser();
        List<CalendarEvent> calendarEvents = new ArrayList<>();
        for(Calendar calendar : user.getCalendarSettings().getCalendars()){
            calendarEvents.addAll(CalendarEventAdapter.addCalenderSettingsToEvent(
                    gCalendarService.getCalendarEvents(user.getUsername(), calendar.getUrl()),
                    calendar));
        }
        return calendarEvents.stream()
                .sorted(Comparator.comparing(CalendarEvent::getStart))
                .collect(toList());
    }

    @RequestMapping("/restGetCalendarDays")
    public List<CalendarDay> getCalendarDays(){
        User user = userProvider.getUser();
        List<CalendarEvent> calendarEvents = new ArrayList<>();
        List<Calendar> calendars = new ArrayList<>();
        calendars.addAll(user.getCalendarSettings().getCalendars());
        for(Calendar calendar : calendars){
            if(calendar.getType().equals(CalendarProvider.GOOGLE.getName())){
                calendarEvents.addAll(CalendarEventAdapter.addCalenderSettingsToEvent(
                        gCalendarService.getCalendarEvents(user.getUsername(), calendar.getUrl()),calendar));
            }
            else if(calendar.getType().equals(CalendarProvider.ICALENDAR.getName())){
                BiweeklyService biweeklyService = new BiweeklyService();
                calendarEvents.addAll(CalendarEventAdapter.addCalenderSettingsToEvent(
                        biweeklyService.getCalendarEvents(calendar.getUrl()),calendar));
            }
        }
        int max = (user.getCalendarSettings().getMaxDays() <= calendarEvents.size()) ?
                user.getCalendarSettings().getMaxDays(): calendarEvents.size();
        return CalendarDayBuilder.getCalendarDays(calendarEvents)
                .stream()
                .sorted(Comparator.comparing(CalendarDay::getDay))
                .collect(toList()).subList(0,max);
    }

    @RequestMapping("/restGetAvailableCalendarsOfType")
    public List<Calendar> getAvailableCalendarsOfType(@RequestParam("type") String type ){
        User user = userProvider.getUser();
        List<Calendar> availableCalendars = gCalendarService.getCalendars(user.getUsername());

        Set<Long> selectedCalendarIds =
                user.getCalendarSettings().getCalendars().stream()
                        .map(Calendar::getId)
                        .collect(toSet());

        return availableCalendars.stream()
                .filter(calendar -> !selectedCalendarIds.contains(calendar.getId()))
                .collect(toList());
    }


}
