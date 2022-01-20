package org.school.diary.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.school.diary.NotFoundException;
import org.school.diary.dao.LessonHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class LessonHourServiceImplTest {

    @Autowired
    private LessonHourService lessonHourService;

    @MockBean
    private LessonHourRepository lessonHourRepository;

    @Test
    public void shouldThrowExceptionWhenLessonNotExists(){
        Mockito.when(lessonHourRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> lessonHourService.getLessonHourById(1)).withMessage("Given lesson not exists");
    }
}