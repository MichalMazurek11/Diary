package org.school.diary.service;

import lombok.AllArgsConstructor;
import org.school.diary.dao.WeekdayRepository;
import org.school.diary.model.Weekday;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WeekdayServiceImpl implements WeekdayService {

    private final WeekdayRepository weekdayRepository;

    @Override
    public void saveAllWeekdays(List<Weekday> weekdays) {
        weekdayRepository.saveAll(weekdays);
    }

    @Override
    public List<Weekday> findAll() {
        return weekdayRepository.findAll();
    }
}
