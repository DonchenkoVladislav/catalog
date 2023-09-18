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
import ru.svoi.catalog.dto.ByteArrayInputStreamSerializer;
import ru.svoi.catalog.dto.ByteArrayResourceSerializer;
import ru.svoi.catalog.dto.Catalog;
import ru.svoi.catalog.dto.Item;
import ru.svoi.catalog.dto.MainPhotoDto;
import ru.svoi.catalog.models.ApartmentDescription;
import ru.svoi.catalog.models.MainImage;
import ru.svoi.catalog.repo.ApartmentRepository;
import ru.svoi.catalog.repo.MainImageRepository;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CatalogService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private MainImageRepository mainImageRepository;

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
}