package ru.svoi.catalog.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.svoi.catalog.models.ApartmentDescription;

import java.util.List;
import java.util.stream.StreamSupport;

@Repository
public interface ApartmentRepository extends CrudRepository<ApartmentDescription, Long> {

    default List<Long> getIdsFromCity(String city) {
        return StreamSupport.stream(findAll().spliterator(), true)
                .filter(apartmentDescription -> apartmentDescription.getCity().equals(city))
                .map(ApartmentDescription::getId)
                .toList();
    }
}
