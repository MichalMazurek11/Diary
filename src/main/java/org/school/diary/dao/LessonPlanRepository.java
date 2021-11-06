package org.school.diary.dao;


import org.school.diary.model.LessonPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonPlanRepository extends JpaRepository<LessonPlan, Long> {
}
