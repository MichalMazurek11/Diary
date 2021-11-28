package org.school.diary.dao;

import org.school.diary.model.Weekday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekdayRepository extends JpaRepository<Weekday,Long> {

    Weekday findWeekdayByDayName(String name);
}
