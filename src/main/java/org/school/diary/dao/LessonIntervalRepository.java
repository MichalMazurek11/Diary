package org.school.diary.dao;


import org.school.diary.model.LessonInterval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonIntervalRepository extends JpaRepository<LessonInterval, Long> {
}
