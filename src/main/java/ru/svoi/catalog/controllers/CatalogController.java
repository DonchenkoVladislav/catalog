package ru.svoi.catalog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.svoi.catalog.dto.Catalog;
import ru.svoi.catalog.dto.FullApartmentInfo;
import ru.svoi.catalog.dto.MainPhotoDto;
import ru.svoi.catalog.models.Image;
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

    // Отправить все фото для отображение на раскрытой карточке объекта
    @RequestMapping(value = "/upload-all-photos-to-object", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Image>> getAllPhotoToObject(
            @RequestParam("apartmentId") String apartmentId) {
        return catalogService.getAllPhotoListToOneObject(apartmentId);
    }

    //Отправить полную инофрмацию об объекте для отображения на отдельной странице
    @GetMapping("/apartment-info")
    public @ResponseBody FullApartmentInfo getApartmentInfo(
            @RequestParam("apartmentId") String apartmentId,
            @RequestParam("apartmentDate") String apartmentDate) {

        return catalogService.getFullApartmentInfo(apartmentId, apartmentDate);
    }
}
