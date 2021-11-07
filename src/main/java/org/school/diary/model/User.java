package org.school.diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User  {

    @Id
    @GeneratedValue
    private long id;

//    @Column(unique = true)
    private String pesel;

    private String password;

//    @Column(unique = true)
    private String email;

    //Chyba do usuniecia ?
    private String type;

    private String role;



    public List<String> getRoleList() {
        if (this.role.length() > 0) {
            return Arrays.asList(this.role.split(","));
        }

        return new ArrayList<String>();
    }
}
