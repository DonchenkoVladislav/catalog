package ru.svoi.catalog.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MainImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long saveTime;

    /**
     * @Lob - храним большой файл
     * @Column(columnDefinition = "MEDIUMBLOB") - меняем значние по-умолчанию TINYBLOB на MEDIUMBLOB - больишй размер
     */
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] data;

    public MainImage() {}
}

