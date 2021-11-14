package org.school.diary.mappers;

import org.school.diary.dto.UserDTO;
import org.school.diary.model.Parent;
import org.school.diary.model.Role;
import org.school.diary.model.common.Director;
import org.school.diary.model.common.PersonRelatedWithSchool;
import org.school.diary.model.common.Student;
import org.school.diary.model.common.Teacher;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class SignedUserMapper {

    public PersonRelatedWithSchool mapPersonRelatedWithSchoolToSpecificImplementation(UserDTO userDTO) {
        PersonRelatedWithSchool personRelatedWithSchool = new PersonRelatedWithSchool();
        switch (userDTO.getPersonRole().toLowerCase()) {
            case "teacher":
                personRelatedWithSchool = new Teacher();
                break;
            case "parent":
                personRelatedWithSchool = new Parent();
                break;
            case "student":
                personRelatedWithSchool = new Student();
                break;
            case "director":
                personRelatedWithSchool = new Director();
                break;
        }
        personRelatedWithSchool.setEmail(userDTO.getEmail());
//        personRelatedWithSchool.setFirstName(userDTO.get);//TODO add firstname to form
        personRelatedWithSchool.setDateBirth(LocalDate.parse(userDTO.getBirthDate()));
        personRelatedWithSchool.setPesel(userDTO.getPesel());
        return personRelatedWithSchool;
    }
}
