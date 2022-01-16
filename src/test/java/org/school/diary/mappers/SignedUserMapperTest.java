package org.school.diary.mappers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.school.diary.dto.UserDTO;
import org.school.diary.model.common.PersonRelatedWithSchool;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SignedUserMapperTest {

    private static List<UserDTO> userDTOS;
    private static String firstName = "Jan";
    private static String login = "Jan123";
    private static String lastName = "Nowak";
    private static String email = "Janek@wp.pl";
    private static String password = "jan1234";
    private static String pesel = "12345678901";
    private static String birthdate = "2000-01-01";
    private static List<String> roles = List.of("teacher","parent","student","director");
    @BeforeAll
    public static void createUserDtos(){
        userDTOS =  roles.stream()
                .map(role -> new UserDTO(firstName,login,lastName,email,password,pesel,birthdate,role))
                .collect(Collectors.toList());
    }

    @Test
    public void shouldMapUserFromDtoToAllImplementationsOfPersonRelatedWithSchool(){
        //given
        Set<? extends Class<? extends PersonRelatedWithSchool>> createdImplementations = userDTOS.stream().map(SignedUserMapper::mapPersonRelatedWithSchoolToSpecificImplementation)
                .map(PersonRelatedWithSchool::getClass).collect(Collectors.toSet());
        //when
        List<PersonRelatedWithSchool> peopleRelatedWithSchool = userDTOS.stream().map(SignedUserMapper::mapPersonRelatedWithSchoolToSpecificImplementation).collect(Collectors.toList());
        createdImplementations.removeIf(implementation -> peopleRelatedWithSchool.stream().anyMatch(personRelatedWithSchool -> personRelatedWithSchool.getClass().getName().equals(implementation.getName())));
        boolean anyPersonHasImplementedDetails = peopleRelatedWithSchool.stream().anyMatch(personRelatedWithSchool ->
                personRelatedWithSchool.getFirstName().equals(firstName)
                && personRelatedWithSchool.getLastName().equals(lastName)
                && personRelatedWithSchool.getEmail().equals(email)
                && personRelatedWithSchool.getPesel().equals(pesel)
                && personRelatedWithSchool.getDateBirth().equals(LocalDate.parse(birthdate)));
        //then
        Assertions.assertThat(createdImplementations.size()).isEqualTo(0); // sprawdź czy udało się przemapować wszystkie implementacje personRelatedWithSchool
        Assertions.assertThat(anyPersonHasImplementedDetails).isEqualTo(true);
    }

}