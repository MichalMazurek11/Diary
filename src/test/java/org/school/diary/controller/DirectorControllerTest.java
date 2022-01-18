package org.school.diary.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.school.diary.config.UserPrincipalDetailService;
import org.school.diary.dao.SubjectRepository;
import org.school.diary.model.Subject;
import org.school.diary.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

@WebMvcTest(DirectorController.class)
public class DirectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectService subjectService;


    @Test
    public void shouldGetTeacher() throws Exception {
        Mockito.when(subjectService.listAllSubject()).thenReturn(Collections.singletonList(new Subject()));
        mockMvc
                .perform(MockMvcRequestBuilders.get("/home/director/dodaj_nauczyciela"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("director/add-teacher"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listSubjects"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("teacherDTO"));
    }

//    mvc
//            .perform(get("/admin").with(user("admin").password("pass").roles("USER","ADMIN")))

}