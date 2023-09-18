package ru.svoi.catalog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.svoi.catalog.dto.Catalog;
import ru.svoi.catalog.dto.MainPhotoDto;
import ru.svoi.catalog.models.MainImage;
import ru.svoi.catalog.services.CatalogService;

import java.util.List;

@Controller
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    //Список всех квартир
    @PostMapping("/catalog")
    @CrossOrigin(origins = "*")
    public @ResponseBody Catalog getCatalog(@RequestParam(required = false) String city,
                                            @RequestParam(required = false) String date) {
        return catalogService.createCatalog(city, date);
    }

    // Отправить главные фото для отображения
    @RequestMapping(value = "/upload-main-photos", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<MainPhotoDto>> getMainPhoto(
            @RequestParam("mainImageId") String mainPhotoidList) {
        return catalogService.getMainPhotoList(mainPhotoidList);
    }
}
