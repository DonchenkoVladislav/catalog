package ru.svoi.catalog.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Calendar;

@Getter
@Builder
public class BookingDate {

    private Calendar date;
    private Integer summary;
}
