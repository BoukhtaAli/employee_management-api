package ma.java.tutorials.employees.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO implements Serializable {

    private Long id;

    @NotBlank(message = "username is require")
    private String username;

    @NotBlank(message = "username is require")
    private String password;

    @NotBlank(message = "username is require")
    private String email;

    private Set<RoleDTO> roles = new HashSet<>();
}
