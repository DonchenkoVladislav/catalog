package ru.svoi.catalog.dto;

import lombok.Builder;
import lombok.Getter;
import ru.svoi.catalog.models.ApartmentDescription;

@Getter
@Builder
public class Item {

    private Long id;
    private String view, name, city, coordinates, beds, conveniences, services;

    private String description;
    private int space, adult, children, fromDay, summary;

    private Long mainImageId;

    public static class ItemBuilder {
        public ItemBuilder fromApartment(ApartmentDescription apartmentDescription) {
            this.id = apartmentDescription.getId();
            this.view = apartmentDescription.getView();
            this.name = apartmentDescription.getName();
            this.city = apartmentDescription.getCity();
            this.coordinates = apartmentDescription.getCoordinates();
            this.beds = apartmentDescription.getBeds();
            this.conveniences = apartmentDescription.getConveniences();
            this.services = apartmentDescription.getServices();
            this.description = apartmentDescription.getDescription();
            this.space = apartmentDescription.getSpace();
            this.adult = apartmentDescription.getAdult();
            this.children = apartmentDescription.getChildren();
            this.fromDay = apartmentDescription.getFromDay();
            this.summary = apartmentDescription.getSummary();
            this.mainImageId = apartmentDescription.getMainImageId();
            return this;
        }
    }
}
