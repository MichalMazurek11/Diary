package org.school.diary.service;

import lombok.AllArgsConstructor;
import org.school.diary.dao.AnswerToHomeworkRepository;
import org.school.diary.model.AnswerToHomework;
import org.school.diary.model.ClassGroup;
import org.school.diary.model.Homework;
import org.school.diary.model.enums.StateAnswaerToHomework;
import org.school.diary.model.common.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AnswerToHomeworkServiceImpl implements AnswerToHomeworkService {

    private final AnswerToHomeworkRepository answerToHomeworkRepository;


    @Override
    public List<AnswerToHomework> findAll() {
        return answerToHomeworkRepository.findAll();
    }

    public List<AnswerToHomework> generateAndSaveEmptyAnswer(ClassGroup classGroup,  Homework homework) {
        List<AnswerToHomework> AnswerToHomework = new ArrayList<>();
        StateAnswaerToHomework state = StateAnswaerToHomework.NIEODDANA;
        String answerForHomework = "";
        classGroup.getStudents().forEach(student -> AnswerToHomework.add(new AnswerToHomework(answerForHomework,state,homework,student)));
        saveAllAnswer(AnswerToHomework);
        return AnswerToHomework;
    }

    @Override
    public List<AnswerToHomework> findAllByHomework(Homework homework) {
        return answerToHomeworkRepository.findAllByHomework(homework);
    }

    @Override
    public void saveAllAnswer(List<AnswerToHomework> answerToHomeworks) {
        answerToHomeworkRepository.saveAll(answerToHomeworks);
    }

    @Override
    public AnswerToHomework findById(long id) {
        return answerToHomeworkRepository.findById(id);
    }

    @Override
    public AnswerToHomework findByHomeworkAndStudent(Homework homework, Student student) {
        return answerToHomeworkRepository.findByHomeworkAndStudent(homework,student);
    }

    @Override
    public void saveAnswer(AnswerToHomework answerToHomework) {
        answerToHomeworkRepository.save(answerToHomework);
    }


}
