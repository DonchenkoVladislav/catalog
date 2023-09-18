package ru.svoi.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.io.Resource;

@Getter
@AllArgsConstructor
public class MainPhotoDto {

    private Long imgId;
    private Resource resource;

    public MainPhotoDto() {

    }
}
