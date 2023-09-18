package ru.svoi.catalog.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
public class ApartmentDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String view, name, city, coordinates, beds, conveniences, services;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    private int space, adult, children, fromDay, summary;

    private Long saveTime;
    private Long mainImageId;

    public ApartmentDescription() {
    }
}
