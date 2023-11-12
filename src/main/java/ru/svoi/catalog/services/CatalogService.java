package ru.svoi.catalog.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import ru.svoi.catalog.dto.BookingDate;
import ru.svoi.catalog.dto.ByteArrayInputStreamSerializer;
import ru.svoi.catalog.dto.ByteArrayResourceSerializer;
import ru.svoi.catalog.dto.Catalog;
import ru.svoi.catalog.dto.FullApartmentInfo;
import ru.svoi.catalog.dto.Item;
import ru.svoi.catalog.dto.MainPhotoDto;
import ru.svoi.catalog.models.ApartmentDescription;
import ru.svoi.catalog.models.Image;
import ru.svoi.catalog.models.MainImage;
import ru.svoi.catalog.repo.ApartmentRepository;
import ru.svoi.catalog.repo.ImageRepository;
import ru.svoi.catalog.repo.MainImageRepository;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

import static ru.svoi.catalog.services.CalendarService.parseDateRange;

@Service
public class CatalogService {

    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private MainImageRepository mainImageRepository;
    @Autowired
    private ImageRepository imageRepository;

// написать метод формирования ApartmentItem
    public Catalog createCatalog(String city, String date) {
        var idList = apartmentRepository.getIdsFromCity(city);

        List<Item> itemList = new ArrayList<>();

        idList.forEach(id -> {
            var apartment = getApartmentById(id);
            itemList.add(Item.builder().fromApartment(apartment).build());
        });

        return new Catalog(itemList);
    }

    private ApartmentDescription getApartmentById(Long id) {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Извините, такой объект не существует. Мы скоро исправим данную ошибку О" + id));
    }

    public ResponseEntity<List<MainPhotoDto>> getMainPhotoList(String mainPhotoIdList) {

        List<MainPhotoDto> resources = new ArrayList<>();

        parseStringToListLong(mainPhotoIdList).forEach(id -> {
            var mainPhoto = mainImageRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException(String.format("Фото с id \"%s\" не существует")));
            resources.add(new MainPhotoDto(id, new ByteArrayResource(mainPhoto.getData())));
        });

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    public ResponseEntity<List<Image>> getAllPhotoListToOneObject(String apartmentId) {

        var apartmentIdLong = Long.parseLong(apartmentId.trim());

        var saveTimeToFindPhotos = apartmentRepository.findById(apartmentIdLong)
                .orElseThrow(() -> new NoSuchElementException("Не найден объект в БД с id = " + apartmentIdLong))
                .getSaveTime();

        List<Image> resources = StreamSupport.stream(imageRepository.findAll().spliterator(), false)
                .filter(image -> image.getSaveTime().equals(saveTimeToFindPhotos))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    public FullApartmentInfo getFullApartmentInfo(String apartmentId, String dates) {

        var calendarList = parseDateRange(dates);

        var apartment = apartmentRepository.findById(Long.parseLong(apartmentId.trim()))
                .orElseThrow(() -> new NoSuchElementException("Такие апартаменты не найдены - Id-" + apartmentId));

        var summary = apartmentRepository.getSummaryByDateList(apartment, calendarList);
        var bookingDates = apartmentRepository.getBookingDatesList(apartment, calendarList);

        var mainImage = mainImageRepository.findById(apartment.getMainImageId())
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Не найдено главное фото (id=%s) объекта (id=%s)",
                                apartment.getMainImageId(), apartmentId)));

        var imagesList = StreamSupport.stream(imageRepository.findAll().spliterator(), false)
                .filter(image -> image.getSaveTime().equals(apartment.getSaveTime()))
                .toList();

        return FullApartmentInfo.builder()
                .id(apartment.getId())
                .name(apartment.getName())
                .space(apartment.getSpace())
                .nights(countNights(calendarList))
                .bookingDates(bookingDates)
                .coordinates(apartment.getCoordinates())
                .description(apartment.getDescription())
                .fromDay(apartment.getFromDay())
                .adult(apartment.getAdult())
                .children(apartment.getChildren())
                .beds(apartment.getBeds())
                .services(apartment.getServices())
                .conveniences(apartment.getConveniences())
                .view(apartment.getView())
                .city(apartment.getCity())
                .summary(summary)
                .mainImage(mainImage)
                .images(imagesList)
                .build();
    }

    // Хз зачем это в контроллерах - chatGPT сгенирил, без него не работает
    // Конфигурация для правильной сериализации ByteArrayResource и ByteArrayInputStrem
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(ByteArrayResource.class, new ByteArrayResourceSerializer());
        module.addSerializer(ByteArrayInputStream.class, new ByteArrayInputStreamSerializer());

        objectMapper.registerModule(module);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        return converter;
    }

    private static List<Long> parseStringToListLong(String idList) {

        String cleanedInput = idList.replace("[", "").replace("]", "");
        String[] numbers = cleanedInput.split(",");

        List<Long> resultList = new ArrayList<>();

        for (String number : numbers) {
            Long parsedNumber = Long.parseLong(number.trim());
            resultList.add(parsedNumber);
        }

        return resultList;
    }

    private String countNights(List<Calendar> calendarList) {
        return switch (calendarList.size() % 10) {
            case 1 -> calendarList.size() + " ночь";
            case 2, 3, 4 -> calendarList.size() + " ночи";
            default -> calendarList.size() + " ночей";
        };
    }
}