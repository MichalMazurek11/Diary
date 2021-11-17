package org.school.diary.service;

import org.school.diary.model.Weekday;

import java.util.List;

public interface WeekdayService {

    void saveAllWeekdays(List<Weekday> weekdays);

    List<Weekday> findAll();
}
