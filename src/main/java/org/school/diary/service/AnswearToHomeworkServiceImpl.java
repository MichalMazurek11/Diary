package org.school.diary.service;

import lombok.AllArgsConstructor;
import org.school.diary.dao.AnswearToHomeworkRepository;
import org.school.diary.model.AnswearToHomework;
import org.school.diary.model.ClassGroup;
import org.school.diary.model.Homework;
import org.school.diary.model.enums.StateAnswaerToHomework;
import org.school.diary.model.common.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AnswearToHomeworkServiceImpl implements AnswearToHomeworkService{

    private final AnswearToHomeworkRepository answearToHomeworkRepository;


    @Override
    public List<AnswearToHomework> findAll() {
        return answearToHomeworkRepository.findAll();
    }

    public List<AnswearToHomework> generateAndSaveEmptyAnswear(ClassGroup classGroup,  Homework homework) {
        List<AnswearToHomework> AnswearToHomework = new ArrayList<>();
        StateAnswaerToHomework state = StateAnswaerToHomework.NIEODDANA;
        String answearForHomework = "";
        classGroup.getStudents().forEach(student -> AnswearToHomework.add(new AnswearToHomework(answearForHomework,state,homework,student)));
        saveAllAnswear(AnswearToHomework);
        return AnswearToHomework;
    }

    @Override
    public List<AnswearToHomework> findAllByHomework(Homework homework) {
        return answearToHomeworkRepository.findAllByHomework(homework);
    }

    @Override
    public void saveAllAnswear(List<AnswearToHomework> answearToHomeworks) {
        answearToHomeworkRepository.saveAll(answearToHomeworks);
    }

    @Override
    public AnswearToHomework findById(long id) {
        return answearToHomeworkRepository.findById(id);
    }

    @Override
    public AnswearToHomework findByHomeworkAndStudent(Homework homework, Student student) {
        return answearToHomeworkRepository.findByHomeworkAndStudent(homework,student);
    }

    @Override
    public void saveAnswear(AnswearToHomework answearToHomework) {
        answearToHomeworkRepository.save(answearToHomework);
    }


}
