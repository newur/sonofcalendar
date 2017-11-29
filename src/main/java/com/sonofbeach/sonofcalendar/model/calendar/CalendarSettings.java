package com.sonofbeach.sonofcalendar.model.calendar;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CalendarSettings {

    @Id
    @GeneratedValue
    private Long id;

    private String calendarType;
    private int maxDays;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Calendar> calendars = new ArrayList<>();

    public CalendarSettings(){}

    public String getCalendarType() {
        return calendarType;
    }

    public void setCalendarType(String calendarType) {
        this.calendarType = calendarType;
    }

    public List<Calendar> getCalendars() {
        return calendars;
    }

    public void setCalendars(List<Calendar> calendars) {
        this.calendars = calendars;
    }

    public int getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
