package ru.svoi.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Catalog {
    List<Item> items;
}
