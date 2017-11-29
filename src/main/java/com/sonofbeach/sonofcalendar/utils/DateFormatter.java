package com.sonofbeach.sonofcalendar.utils;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateFormatter {

    public static String getDayPrefix(LocalDate day){
        LocalDate today = LocalDate.now();
        if(day.equals(today))
            return "Heute,";
        else if(day.equals(today.plusDays(1)))
            return "Morgen,";
        else
            return "";
    }

    public static String getDayString(LocalDate day){
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();
        if(day.getMonthValue()==currentMonth && day.getYear()==currentYear)
            return day.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.GERMAN) + ", " + day.getDayOfMonth()+".";
        else
            return day.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.GERMAN) + ", " + day.getDayOfMonth()+"."
                    +day.getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN);
    }

}
