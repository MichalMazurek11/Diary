package org.school.diary.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.school.diary.dao.RoleRepository;
import org.school.diary.dao.UserRepository;
import org.school.diary.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceImplTest {


    private static UserDTO userDTO;
    private static String firstName = "Jan";
    private static String login = "Jan123";
    private static String lastName = "Nowak";
    private static String email = "Janek@wp.pl";
    private static String password = "jan1234";
    private static String pesel = "12345678901";
    private static String birthdate = "2000-01-01";
    private static String role = "director";

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

//    private TeacherService teacherService;
//    private ParentService parentService;
//    private StudentService studentService;
//    private DirectorService directorService;
    @BeforeAll
    public static void init(){
        userDTO = new UserDTO(firstName,login,lastName,email,password,pesel,birthdate,role);
    }


    @Test
    public void shouldSaveUserBasedOnDTO(){
//        userRepository.
    }

    @Test
    public void shouldFindUserBasedOnEmail(){
//        userRepository = mock(UserRepository.class);
        when(userRepository.existsUserByPersonRelatedWithSchoolEmail(email)).thenReturn(true);
        Assertions.assertThat(userService.existsUserByEmail(email)).isEqualTo(true);
        verify(userRepository,times(1)).existsUserByPersonRelatedWithSchoolEmail(email);
    }
}