package ru.svoi.catalog.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.svoi.catalog.models.Image;
import ru.svoi.catalog.models.MainImage;

import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class FullApartmentInfo {

    @Id
    private Long id;
    private String view, name, city, coordinates, beds, conveniences, services;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    private String nights;
    private int space, adult, children, fromDay, summary;

    private Long saveTime;
    private Long mainImageId;

    private MainImage mainImage;
    private List<Image> images;

    private List<BookingDate> bookingDates;

    public FullApartmentInfo() {
    }
}
