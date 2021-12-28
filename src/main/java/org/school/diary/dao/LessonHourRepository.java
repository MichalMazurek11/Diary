package org.school.diary.dao;


import org.school.diary.model.ClassGroup;
import org.school.diary.model.LessonHour;
import org.school.diary.model.LessonInterval;
import org.school.diary.model.Weekday;
import org.school.diary.model.common.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonHourRepository extends JpaRepository<LessonHour, Long> {

    List<LessonHour> findAllByClassGroup(ClassGroup classGroup);
    List<LessonHour> findLessonHourByClassGroupAndWeekday(ClassGroup classGroup, Weekday weekday);

    List<LessonHour> findAllByTeacher(Teacher teacher);

    @Override
    void deleteAll();
}
