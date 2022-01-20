package org.school.diary.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.school.diary.dao.WeekdayRepository;
import org.school.diary.model.Weekday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeekdayServiceImplTest {
    List<Weekday> weekdays;
    @BeforeEach
    public void setUp() {
        weekdays = Arrays.asList(
                new Weekday(1, "poniedziałek"),
                new Weekday(2, "wtorek"),
                new Weekday(3, "środa"),
                new Weekday(4, "czwartek"),
                new Weekday(5, "piątek")
        );
    }

    @Autowired
    private WeekdayRepository weekdayRepository;

    @Autowired
    private WeekdayService weekdayService;

    @Test
    void saveAllWeekdays() {
        weekdayService.saveAllWeekdays(weekdays);
        Assertions.assertThat(weekdayService.findAll()).isEqualTo(weekdays);
    }

    @Test
    void findWeekdayByDayName() {
        weekdayRepository.save(weekdays.get(0));
        Weekday poniedzialek = weekdayService.findWeekdayByDayName("poniedziałek");
        Assertions.assertThat(poniedzialek).isEqualTo(weekdays.get(0));
    }
}