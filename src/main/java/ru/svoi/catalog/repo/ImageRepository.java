package ru.svoi.catalog.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.svoi.catalog.models.Image;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
}

