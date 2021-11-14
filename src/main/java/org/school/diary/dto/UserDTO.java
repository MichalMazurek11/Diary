package org.school.diary.dto;

import lombok.*;
import org.school.diary.model.Role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    @NotNull(message = "Cannot be null")
    @Size(min=2, max=30)
    @NotEmpty
    private String email;
    @NotNull(message = "Cannot be null")
    @Size(min=2, max=30)
    @NotEmpty
    private String password;
    @NotNull(message = "Cannot be null")
    @Size(min=2, max=30)
    @NotEmpty
    private String pesel;
    @NotNull(message = "Cannot be null")
    @Size(min=2, max=30)
    @NotEmpty
    private String birthDate;
    @NotNull(message = "Cannot be null")
    @Size(min=2, max=30)
    @NotEmpty
    private String personRole;
}
