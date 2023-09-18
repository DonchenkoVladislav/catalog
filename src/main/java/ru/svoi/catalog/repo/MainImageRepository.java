package ru.svoi.catalog.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.svoi.catalog.models.MainImage;

@Repository
public interface MainImageRepository extends CrudRepository<MainImage, Long> {

}

