package ma.java.tutorials.employees.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter @NoArgsConstructor
public class AuthRequestDTO {

    @NotBlank(message = "Missing Required Field")
    private String username;

    @NotBlank(message = "Missing Required Field")
    private String password;
}
