package ru.svoi.catalog.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.svoi.catalog.dto.BookingDate;
import ru.svoi.catalog.models.ApartmentDescription;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Repository
public interface ApartmentRepository extends CrudRepository<ApartmentDescription, Long> {

    default List<Long> getIdsFromCity(String city) {
        return StreamSupport.stream(findAll().spliterator(), true)
                .filter(apartmentDescription -> apartmentDescription.getCity().equals(city))
                .map(ApartmentDescription::getId)
                .toList();
    }

    default Integer getSummaryByDateList(ApartmentDescription apartment, List<Calendar> calendarList) {

        return getBookingDatesList(apartment, calendarList).stream()
                .map(BookingDate::getSummary)
                .reduce(0, Integer::sum);
    }

    default List<BookingDate> getBookingDatesList(ApartmentDescription apartment, List<Calendar> calendarList) {

        List<BookingDate> bookingDateList = new ArrayList<>();

        calendarList.forEach(
                calendar -> bookingDateList.add(
                BookingDate.builder()
                        .date(calendar)
                        //todo дописать метод для работы с тарифами
                        .summary(apartment.getSummary())
                        .build()
                )
        );

        return bookingDateList;
    }
}
