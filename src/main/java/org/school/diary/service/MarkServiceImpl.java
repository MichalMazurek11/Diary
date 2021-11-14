package org.school.diary.service;

import org.school.diary.dao.MarkRepository;
import org.school.diary.model.Mark;
import org.school.diary.model.common.Student;
import org.school.diary.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkServiceImpl implements  MarkService{

    @Autowired
    MarkRepository markRepository;

    @Override
    public void save(Mark mark) {
        markRepository.save(mark);
    }

    @Override
    public List<Mark> findAllByStudentAndSubject(Student student, Subject subject) {
        return markRepository.findAllByStudentAndSubject(student,subject);
    }

//    @Override
//    public void deleteMark(long id) {
//        markRepository.delete(id);
//    }




}
