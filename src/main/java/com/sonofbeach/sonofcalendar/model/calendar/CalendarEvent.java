package com.sonofbeach.sonofcalendar.model.calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class CalendarEvent {

    private String name;
    private String description;
    private String location;
    private String color;
    private LocalDateTime start;
    private LocalDateTime end;

    public CalendarEvent() {
    }

    public CalendarEvent(String name, String description, String location, LocalDateTime start, LocalDateTime end) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.start = start;
        this.end = end;

    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStartAsString(){
        if(start!=null && !isAllday())
            return start.format((DateTimeFormatter.ofPattern("HH:mm")));
        else return "";
    }

    public String getEndAsString(){
        if(end!=null && !isAllday())
            return end.format((DateTimeFormatter.ofPattern("HH:mm")));
        else return "";
    }

    public boolean isMultiday(){
        return (!this.isAllday() && !this.start.toLocalDate().equals(this.end.toLocalDate()));
    }

    public boolean isAllday(){
        return this.start.toLocalTime().equals(LocalDateTime.MIN.toLocalTime());
    }

    public List<LocalDate> getMiddleDays(){
        List<LocalDate> days = new ArrayList<>();
        LocalDate currDay = start.toLocalDate().plusDays(1);
        if(isMultiday()){
            while (!currDay.equals(end.toLocalDate())){
                days.add(currDay);
                currDay = currDay.plusDays(1);
            }
        }
        return days;
    }

    public int getDaysNo(){
        return ((int) DAYS.between(start.toLocalDate(), end.toLocalDate()) + 1);
    }

    @Override
    public String toString() {
        return "CalendarEvent{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
