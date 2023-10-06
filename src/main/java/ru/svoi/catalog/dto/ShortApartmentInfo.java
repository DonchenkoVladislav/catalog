package ru.svoi.catalog.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.svoi.catalog.models.Image;
import ru.svoi.catalog.models.MainImage;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ShortApartmentInfo {


    //todo Использовать вместо ApartmentDescription для формирования страницы каталога
    @Id
    private Long id;
    private String view, name, city, coordinates, beds, conveniences, services;

    private int space, adult, children, fromDay, summary;

    public ShortApartmentInfo() {
    }
}
