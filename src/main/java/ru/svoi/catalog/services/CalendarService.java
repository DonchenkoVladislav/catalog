package ru.svoi.catalog.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarService {

    public static List<Calendar> parseDateRange(String dateString) {
        List<Calendar> dateList = new ArrayList<>();

        String[] dateParts = dateString.split(":");
        String startDateString = dateParts[0];
        String endDateString = dateParts[1];

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        try {
            Date startDate = dateFormat.parse(startDateString);
            Date endDate = dateFormat.parse(endDateString);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            while (!calendar.getTime().after(endDate)) {
                dateList.add((Calendar) calendar.clone());
                calendar.add(Calendar.DATE, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateList;
    }
}