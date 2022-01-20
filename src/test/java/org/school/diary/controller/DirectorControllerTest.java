package org.school.diary.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.school.diary.config.UserPrincipalDetailService;
import org.school.diary.dao.SubjectRepository;
import org.school.diary.model.Subject;
import org.school.diary.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

@WebMvcTest(DirectorController.class)
@WithMockUser(username="admin",roles={"DIRECTOR"})
public class DirectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectService subjectService;
    @MockBean
    private UserPrincipalDetailService userPrincipalDetailService;
    @MockBean
    private ClassGroupService classGroupService;
    @MockBean
    private UserService userService;
    @MockBean
    private TeacherService teacherService;
    @MockBean
    private StudentService studentService;
    @MockBean
    private LessonHourService lessonHourService;
    @MockBean
    private AnnouncementService announcementService;
    @MockBean
    private DirectorService directorService;
    @MockBean
    private RoleService roleService;
    @MockBean
    private ParentService parentService;
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
}